<script setup lang="ts">
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import { Location, Money, View } from "@element-plus/icons-vue";
import type { PropType } from "vue";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType>,
    required: true,
  },
  showLikes: {
    type: Boolean,
    required: false,
    default: false,
  },
});
</script>

<template>
  <div>
    <ScheduleInfoSlot
      :schedule="schedule"
      :font-size="0.85"
      :show-likes="showLikes"
    >
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
      <template v-if="showLikes" v-slot:like-title>
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
