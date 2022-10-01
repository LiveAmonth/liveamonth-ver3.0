<script setup lang="ts">
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import { Location, Money, View } from "@element-plus/icons-vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useSchedule } from "@/composables/schedule/schedule";

defineProps({
  showLikes: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const { currentSchedule } = useSchedule();
const { labelMsg } = useMessageBox();
</script>

<template>
  <div>
    <ScheduleInfoSlot
      :schedule="currentSchedule"
      :font-size="0.85"
      :show-likes="showLikes"
    >
      <template v-slot:period-title>
        <span>
          <el-icon><Calendar /></el-icon>
          {{ `${labelMsg("schedule.period")}:` }}
        </span>
      </template>
      <template v-slot:cost-title>
        <span>
          <el-icon><Money /></el-icon>
          {{ `${labelMsg("schedule.cost")}:` }}
        </span>
      </template>
      <template v-slot:location-title>
        <span>
          <el-icon><Location /></el-icon>
          {{ `${labelMsg("schedule.location")}:` }}
        </span>
      </template>
      <template v-slot:view-title>
        <span>
          <el-icon><View /></el-icon>
          {{ `${labelMsg("view")}:` }}
        </span>
      </template>
      <template v-if="showLikes" v-slot:like-title>
        <span>
          <i class="bi bi-hand-thumbs-up"></i>
          {{ `${labelMsg("like")}:` }}
        </span>
      </template>
    </ScheduleInfoSlot>
  </div>
</template>

<style scoped lang="scss">
.el-row {
  margin-bottom: 10px;
}
.el-icon {
  padding-bottom: 0.09rem;
}
</style>
