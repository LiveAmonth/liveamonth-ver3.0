<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/calendar/ScheduleCalendar.vue";
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import ImageIcon from "@/components/common/ImageIcon.vue";
import ScheduleDetail from "@/components/schedule/detail/ScheduleDetail.vue";
import CommentComponent from "@/components/comment/CommentComponent.vue";
import ProfileCard from "@/components/common/ProfileCard.vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useCalendarEvent } from "@/composables/schedule/calendarEvent";
import { useInteraction } from "@/composables/interaction/interaction";
import { useMessageBox } from "@/composables/common/messageBox";
import { useAuth } from "@/composables/member/auth";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});

const { isLoggedIn } = useAuth();
const { type, currentSchedule, getScheduleContents } = useSchedule();
const { isLiked, error, isPositiveInteraction, reactContent } =
  useInteraction();
const { setContentCollapse } = useCalendarEvent();
const { titleMsg, requireLoginMessageBox } = useMessageBox();
const commentKey = ref<number>(0);

onMounted(async () => {
  await getScheduleContents(Number(props.id));
  if (isLoggedIn.value) {
    await isPositiveInteraction(type, currentSchedule.value.id);
  }
});

const changeCollapse = (id: number) => {
  setContentCollapse(id);
};

const handelLike = async () => {
  if (isLoggedIn.value) {
    await reactContent(type, currentSchedule.value.id).then(() => {
      if (!error.value) {
        isLiked.value
          ? currentSchedule.value.numberOfLikes++
          : currentSchedule.value.numberOfLikes--;
      }
    });
  } else {
    await requireLoginMessageBox();
  }
};
</script>

<template>
  <el-row v-if="currentSchedule.id" class="mb-5">
    <el-col>
      <TitleSlot :title="currentSchedule.title" />
      <el-row :gutter="5">
        <el-col :span="18" class="d-flex justify-content-start flex-column">
          <el-row :gutter="20" class="d-flex justify-content-start">
            <el-col :span="10">
              <el-card class="mb-3" :body-style="{ padding: 0 }">
                <ProfileCard :profile="currentSchedule.profile" />
              </el-card>
            </el-col>
            <el-col :span="14">
              <el-card
                class="mb-3"
                :body-style="{ height: '190px', padding: '9px 20px 0 20px' }"
              >
                <ScheduleInfoSlot :schedule="currentSchedule" :font-size="1.05">
                  <template v-slot:title>
                    <div class="title-like">
                      <SmallTitleSlot :title="titleMsg('schedule.info')" />
                      <el-button class="like-btn" @click="handelLike">
                        <div class="icon-count">
                          <ImageIcon
                            :height="25"
                            :width="25"
                            :url="`/src/assets/image/icon/love${
                              !isLiked ? '' : '-fill'
                            }.png`"
                          />
                          {{ $count(currentSchedule.numberOfLikes) }}
                        </div>
                      </el-button>
                    </div>
                  </template>
                </ScheduleInfoSlot>
              </el-card>
            </el-col>
          </el-row>
          <ScheduleCalendar
            :init-period="currentSchedule.period"
            @select-content="changeCollapse"
          />
        </el-col>
        <el-col :span="6" class="overflow-hidden">
          <ScheduleDetail />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <el-divider />
  <el-row v-if="currentSchedule.id">
    <el-col class="d-flex justify-content-center">
      <CommentComponent
        :key="commentKey"
        :content-id="Number(id)"
        :type="'schedule'"
        :writer="currentSchedule.profile.nickname"
        @refresh="commentKey++"
      />
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.title-like {
  display: flex;
  justify-content: start;
  align-items: center;

  .like-btn {
    border: none;
    padding: 0 5px;
    margin-bottom: 7px;

    &:hover,
    &:focus {
      color: #606266;
      background-color: inherit;
      border-color: #dcdfe6;
    }

    .icon-count {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-bottom: 5px;
      font-size: 1.3rem;
    }
  }
}

.el-calendar-day {
  padding: 0;
}
</style>
