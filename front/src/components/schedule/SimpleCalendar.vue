<script lang="ts" setup>
import { computed, ref } from "vue";
import type { DateRangeType } from "@/modules/types/schedule/ScheduleType";
import { useScheduleStore } from "@/stores/schedule";

const store = useScheduleStore();
const props = defineProps({
  index: {
    type: Number,
    required: true,
  },
});
const scheduleDetail = computed(() => store.scheduleDetails[props.index]);
const period: DateRangeType = {
  startDate: new Date(scheduleDetail.value.period.startDate),
  endDate: new Date(scheduleDetail.value.period.endDate),
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
