<script setup lang="ts">
import { ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import PopoverProfileSlot from "@/components/common/PopoverProfileSlot.vue";
import ScheduleInfoSlot from "@/components/schedule/ScheduleInfoSlot.vue";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});
const { getOtherSchedule } = useSchedule();
const schedule = ref(getOtherSchedule(Number(props.id)));
</script>

<template>
  <div>
    <ScheduleInfoSlot :schedule="schedule" :font-size="0.85">
      <template v-slot:period-title>
        <span>
          <el-icon><Calendar /></el-icon> 기간:
        </span>
      </template>
      <template v-slot:cost-title>
        <span>
          <el-icon><Money /></el-icon> 총 비용:
        </span>
      </template>
      <template v-slot:location-title>
        <span>
          <el-icon><Location /></el-icon> 지역:
        </span>
      </template>
      <template v-slot:view-title>
        <span>
          <el-icon><View /></el-icon> 조회수:
        </span>
      </template>
      <template v-slot:like-title>
        <span> <i class="bi bi-hand-thumbs-up"></i> 좋아요: </span>
      </template>
    </ScheduleInfoSlot>
  </div>
</template>

<style scoped lang="scss">
.el-row {
  margin-bottom: 10px;
}
</style>
