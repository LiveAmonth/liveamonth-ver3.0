<script lang="ts" setup>
import { ref } from "vue";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";
import { useSchedule } from "@/composables/schedule";

const props = defineProps({
  index: {
    type: Number,
    required: true,
  },
});
const { otherSchedules } = useSchedule();
const schedule = ref(otherSchedules.value[props.index]);

const period: DatePeriodType = {
  startDate: new Date(schedule.value.period.startDate),
  endDate: new Date(schedule.value.period.endDate),
};
const fromPage = ref({
  month: (period.startDate as Date).getMonth() + 1,
  year: (period.startDate as Date).getFullYear(),
});
const attrs = ref([
  {
    highlight: {
      start: { fillMode: "outline" },
      base: { fillMode: "light" },
      end: { fillMode: "outline" },
    },
    dates: {
      start: period.startDate,
      end: period.endDate,
    },
  },
]);
</script>

<template>
  <v-calendar :from-page="fromPage" color="teal" :attributes="attrs" />
</template>

<style lang="scss" scoped></style>
