<script setup lang="ts">
import type { PropType } from "vue";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

const props = defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType>,
    required: true,
  },
  fontSize: {
    type: Number,
    required: true,
  },
});

const rowClass = "d-flex justify-content-start";
</script>

<template>
  <div class="title-info" :style="{ fontSize: fontSize + 'rem' }">
    <el-row>
      <slot name="title"></slot>
    </el-row>
    <el-row class="mb-2">
      <div :class="rowClass">
        <slot name="period-title"></slot>
        <span class="value">
          {{ schedule.period.startDate }} ~ {{ schedule.period.endDate }}
        </span>
      </div>
    </el-row>
    <el-row class="mb-2">
      <el-row :class="rowClass">
        <slot name="cost-title"></slot>
        <span class="value"> {{ $filters.makeComma(schedule.cost) }}원 </span>
      </el-row>
    </el-row>
    <el-row class="mb-2">
      <div :class="rowClass">
        <slot name="location-title"></slot>
        <span class="value">
          {{ $t(`city.name.${schedule.city.code}`) }}
        </span>
      </div>
    </el-row>
    <el-row :class="rowClass">
      <div class="view me-4">
        <slot name="view-title"></slot>
        <span class="ms-2">
          {{ schedule.hits }}
        </span>
      </div>
      <div class="like">
        <slot name="like-title"></slot>
        <span class="ms-2">
          {{ schedule.likes }}
        </span>
      </div>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.information {
  .title-info {
    width: 100%;
    margin-left: 25px;

    .el-icon {
      cursor: none;
      margin-right: 2px;
      vertical-align: middle;
    }
  }
}
.value {
  margin-left: 0.5rem;
}
</style>
