<script lang="ts" setup>
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { computed, ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMember } from "@/composables/member/member";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleTypes";

const props = defineProps({
  initialCount: {
    type: Number,
    required: true,
  },
  loginId: {
    type: String,
    required: true,
  },
  isMyPage: {
    type: Boolean,
    required: false,
    default: false,
  },
  maxCount: {
    type: Number,
    required: true,
  },
});
const emits = defineEmits(["refresh"]);
const { memberProfile } = useMember();
const { infiniteSchedules, getInfiniteSchedules, deleteSchedule } =
  useSchedule();

const size = ref<number>(2);
const count = ref<number>(props.initialCount);
const loading = ref<boolean>(false);
const noMore = computed(() => count.value >= props.maxCount);
const disabled = computed(() => loading.value || noMore.value);
const schedules = computed(() => infiniteSchedules(props.isMyPage));
const load = async () => {
  loading.value = true;
  await getInfiniteSchedules(
    props.loginId,
    size.value,
    schedules.value[count.value - 1].id,
    props.isMyPage
  );
  setTimeout(() => {
    count.value += size.value;
    loading.value = false;
  }, 2000);
};

const deleteScheduleBtn = async (scheduleId: number) => {
  await deleteSchedule(scheduleId);
  await getInfiniteSchedules(
    props.loginId,
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
  <div class="infinite-list-wrapper" style="overflow: auto">
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
          <el-col :lg="8" :md="8" :sm="8" :xl="6" :xs="8" class="me-3">
            <SimpleCalendar :period="schedule.period" />
          </el-col>
          <el-col :lg="14" :md="14" :sm="14" :xl="16" :xs="14">
            <ScheduleInfoCard
              :is-my-page="isMyPage"
              :login-id="memberProfile.loginId"
              :schedule="schedule"
              @delete-schedule="deleteScheduleBtn"
            />
            <template v-if="!isMyPage">
              <el-card v-if="schedule.comment" class="reply mb-2">
                <SmallTitleSlot class="mb-3">
                  {{ $t("comment.best") }}
                </SmallTitleSlot>
                <CommentSlot
                  :id="schedule.comment.commentId"
                  :avatar-url="'/src/assets/image/default.jpg'"
                  :is-best="true"
                  :is-reply="false"
                  :is-writer="
                    schedule.profile.nickname ===
                    schedule.comment.profile.nickname
                  "
                >
                  <template v-slot:writer>
                    {{ schedule.comment.profile.nickname }}
                  </template>
                  <template v-slot:elapsedTime>
                    {{ schedule.comment.elapsedTime }}
                  </template>
                  <template v-slot:content>
                    {{ schedule.comment.comment }}
                  </template>
                  <template v-slot:likeCount>
                    {{ schedule.comment.likes }}
                  </template>
                  <template v-slot:dislikeCount>
                    {{ schedule.comment.dislikes }}
                  </template>
                </CommentSlot>
              </el-card>
              <el-card v-else class="reply mb-0">
                {{ $t("comment.no-comment") }}
              </el-card>
            </template>
          </el-col>
        </el-row>
      </li>
    </ul>
    <p v-if="loading">Loading...</p>
    <p v-if="noMore">No more</p>
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

    .reply {
      margin-top: 1.3rem;
      text-align: left;
    }
  }
}
</style>
