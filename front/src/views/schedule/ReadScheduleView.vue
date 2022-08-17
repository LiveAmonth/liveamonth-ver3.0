<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";

import { computed, onMounted, ref } from "vue";
import { useScheduleStore } from "@/stores/schedule";
import { useSchedule } from "@/composables/schedule";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import ScheduleCalendar from "@/components/schedule/ScheduleCalendar.vue";

const props = defineProps({
  nickname: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
});
const store = useScheduleStore();
const { getSchedule } = useSchedule();
const scheduleCard = computed((): ScheduleCardType => store.scheduleCard);
onMounted(async () => {
  console.log(props.nickname);
  console.log(props.title);
  await getSchedule(props.nickname, props.title);
});
</script>

<template>
  <el-row>
    <el-col>
      <TitleSlot>{{ title }}</TitleSlot>
      <ScheduleCalendar />
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.el-calendar-day {
  padding: 0;
}
</style>
