<script lang="ts" setup>
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { useSchedule } from "@/composables/schedule/schedule";
import type { SearchEngineFormType } from "@/modules/types/common/SearchType";

const emits = defineEmits(["applyOption"]);

const { otherSchedules } = useSchedule();

const goSchedule = (data: SearchEngineFormType) => {
  emits("applyOption", data);
};
</script>

<template>
  <div class="schedule-list">
    <ul class="list">
      <li
        v-for="schedule in otherSchedules"
        :key="schedule.id"
        class="list-item py-4"
      >
        <el-row :gutter="5" class="d-flex justify-content-center">
          <el-col :lg="8" :md="8" :sm="8" :xl="6" :xs="8">
            <SimpleCalendar :period="schedule.period" />
          </el-col>
          <el-col :lg="14" :md="14" :sm="14" :xl="16" :xs="14">
            <ScheduleInfoCard
              :schedule="schedule"
              @go-to-member-schedules="goSchedule"
            />
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
          </el-col>
        </el-row>
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
.schedule-list {
  text-align: center;

  .list {
    padding: 0;
    margin: 0;
    list-style: none;

    .list-item {
      & + & {
        margin-top: 10px;
      }

      .reply {
        margin-top: 1.3rem;
        text-align: left;
      }
    }
  }
}
</style>
