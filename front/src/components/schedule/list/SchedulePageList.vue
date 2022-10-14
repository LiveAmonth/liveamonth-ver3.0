<script lang="ts" setup>
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { useSchedule } from "@/composables/schedule/schedule";

const { otherSchedules } = useSchedule();
</script>

<template>
  <div class="schedule-list">
    <ul class="list">
      <li
        v-for="schedule in otherSchedules"
        :key="schedule.id"
        class="list-item py-4"
      >
        <el-row class="d-flex justify-content-center">
          <SimpleCalendar class="me-3" :period="schedule.period" />
          <el-col :span="16" class="schedule-list-card">
            <ScheduleInfoCard :schedule="schedule" />
            <el-card v-if="schedule.bestComment" class="reply mb-2">
              <SmallTitleSlot class="mb-3">
                {{ $t("comment.best") }}
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
}
</style>
