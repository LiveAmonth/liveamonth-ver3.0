<script lang="ts" setup>
import { ref } from "vue";
import type { PropType } from "vue";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleTypes";

const props = defineProps({
  period: {
    type: Object as PropType<DatePeriodType>,
    required: true,
  },
});

const fromPage = ref({
  month: new Date(props.period.startDate).getMonth() + 1,
  year: new Date(props.period.startDate).getFullYear(),
});
const attrs = ref([
  {
    highlight: {
      start: { fillMode: "outline" },
      base: { fillMode: "light" },
      end: { fillMode: "outline" },
    },
    dates: {
      start: props.period.startDate,
      end: props.period.endDate,
    },
  },
]);
</script>

<template>
  <v-calendar :from-page="fromPage" color="gray" :attributes="attrs" />
</template>
