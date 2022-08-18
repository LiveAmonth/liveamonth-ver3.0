<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});
const { getOtherSchedule } = useSchedule();
const scheduleCard = ref<ScheduleCardType>();

onMounted(async () => {
  scheduleCard.value = await getOtherSchedule(Number(props.id));
});
</script>

<template>
  {{ scheduleCard }}
</template>

<style scoped lang="scss"></style>
