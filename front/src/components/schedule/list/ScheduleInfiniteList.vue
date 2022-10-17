<script lang="ts" setup>
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import { Right } from "@element-plus/icons-vue";
import { computed, ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMember } from "@/composables/member/member";
import { useMessageBox } from "@/composables/common/messageBox";

const props = defineProps({
  initialCount: {
    type: Number,
    required: true,
  },
  isMyPage: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emits = defineEmits(["refresh"]);

const { memberProfile } = useMember();
const { infiniteSchedules, getInfiniteSchedules, deleteSchedule } =
  useSchedule();
const { labelMsg, resultMsg, buttonMsg } = useMessageBox();

const size = ref<number>(2);
const count = ref<number>(props.initialCount);
const loading = ref<boolean>(false);
const noMore = computed(
  () => count.value >= memberProfile.value.numberOfSchedules
);
const disabled = computed(() => loading.value || noMore.value);
const schedules = computed(() => infiniteSchedules(props.isMyPage));

const load = async () => {
  loading.value = true;
  await getInfiniteSchedules(
    memberProfile.value.loginId,
    size.value,
    schedules.value[count.value - 1].id,
    props.isMyPage
  );
  setTimeout(() => {
    count.value += size.value;
    loading.value = false;
  }, 500);
};

const deleteScheduleBtn = async (scheduleId: number) => {
  await deleteSchedule(scheduleId);
  await getInfiniteSchedules(
    memberProfile.value.loginId,
    props.initialCount,
    null,
    props.isMyPage
  );
  memberProfile.value.numberOfSchedules--;
  count.value--;
  loading.value = false;
  emits("refresh");
};
</script>

<template>
  <div
    v-if="memberProfile.numberOfSchedules"
    class="infinite-list-wrapper"
    style="overflow: auto"
  >
    <ul
      v-infinite-scroll="load"
      :infinite-scroll-disabled="disabled"
      class="list"
    >
      <li
        v-for="schedule in schedules"
        :key="schedule.id"
        class="list-item py-4"
      >
        <el-row v-if="schedule" class="item d-flex justify-content-center">
          <SimpleCalendar class="me-3" :period="schedule.period" />
          <el-col :span="16" class="schedule-list-card">
            <ScheduleInfoCard
              :is-my-page="isMyPage"
              :login-id="memberProfile.loginId"
              :schedule="schedule"
              @delete-schedule="deleteScheduleBtn"
            />
            <template v-if="!isMyPage">
              <el-card v-if="schedule.bestComment" class="reply mb-2">
                <SmallTitleSlot class="mb-3">
                  {{ labelMsg("comment.best") }}
                </SmallTitleSlot>
                <CommentSlot
                  :id="schedule.bestComment.commentId"
                  :avatar-url="'/src/assets/image/default.jpg'"
                  :is-best="true"
                  :is-reply="false"
                  :is-writer="
                    schedule.profile.nickname ===
                    schedule.bestComment.profile.nickname
                  "
                >
                  <template v-slot:writer>
                    {{ schedule.bestComment.profile.nickname }}
                  </template>
                  <template v-slot:elapsedTime>
                    {{ schedule.bestComment.elapsedTime }}
                  </template>
                  <template v-slot:comment>
                    {{ schedule.bestComment.comment }}
                  </template>
                  <template v-slot:likeCount>
                    {{ schedule.bestComment.numberOfLikes }}
                  </template>
                  <template v-slot:dislikeCount>
                    {{ schedule.bestComment.numberOfDislikes }}
                  </template>
                </CommentSlot>
              </el-card>
              <el-card v-else class="reply mb-0">
                {{ resultMsg("comment.empty") }}
              </el-card>
            </template>
          </el-col>
        </el-row>
      </li>
    </ul>
  </div>
  <div v-else class="empty-post">
    <SmallTitleSlot
      class="d-flex justify-content-center"
      :title="labelMsg('schedule.empty')"
      :title-line="false"
    />
    <LinkSlot class="link" :link="`/my-schedule/${memberProfile.loginId}`">
      <span>
        {{ buttonMsg("schedule.go") }}
      </span>
      <el-icon>
        <Right />
      </el-icon>
    </LinkSlot>
  </div>
</template>

<style lang="scss" scoped>
.infinite-list-wrapper {
  height: 800px;
  text-align: center;

  .list {
    padding: 0;
    margin: 0;
    list-style: none;
  }

  .list-item {
    align-items: center;
    height: 270px;

    & + & {
      margin-top: 10px;
    }

    .schedule-list-card {
      display: flex;
      justify-content: center;
      flex-direction: column;

      .reply {
        margin-top: 1.3rem;
        text-align: left;
      }
    }
  }
}

.empty-post {
  display: flex;
  justify-content: center;
  flex-direction: column;
  color: #808080;

  .link {
    display: flex;
    justify-content: center;
    margin-top: 5px;
    color: #016d7d;
  }
}
</style>
