<script lang="ts" setup>
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import { useSchedule } from "@/composables/schedule/schedule";

const props = defineProps({
  isMain: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const { otherSchedules, bestSchedules } = useSchedule();
const schedules = props.isMain ? bestSchedules.value : otherSchedules.value;
</script>

<template>
  <div class="schedule-list">
    <ul class="list">
      <li
        v-for="(schedule, idx) in schedules"
        :key="schedule.id"
        class="list-item py-4"
      >
        <el-row class="d-flex justify-content-center">
          <SimpleCalendar class="me-3" :period="schedule.period" />
          <el-col :span="16" class="schedule-list-card">
            <ScheduleInfoCard
              :schedule="schedule"
              :rank="isMain ? idx + 1 : 0"
            />
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
