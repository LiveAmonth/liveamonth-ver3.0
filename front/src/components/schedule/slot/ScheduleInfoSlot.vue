<script lang="ts" setup>
import { Money, Location, View } from "@element-plus/icons-vue";
import { useMessageBox } from "@/composables/common/messageBox";
import type { PropType } from "vue";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleTypes";

defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType>,
    required: true,
  },
  fontSize: {
    type: Number,
    required: true,
  },
  showLikes: {
    type: Boolean,
    required: false,
    default: false,
  },
});

const { labelMsg, categoryMsg } = useMessageBox();
</script>

<template>
  <div :style="{ fontSize: fontSize + 'rem' }" class="title-info">
    <div class="details">
      <el-row class="title">
        <el-col class="icon-label">
          <slot name="title"></slot>
        </el-col>
      </el-row>
      <el-row class="period">
        <el-col class="icon-label">
          <el-icon>
            <Calendar />
          </el-icon>
          <span class="label">
            {{ `${labelMsg("schedule.period")}:` }}
          </span>
          <span class="value">
            {{ schedule.period.startDate }} ~ {{ schedule.period.endDate }}
          </span>
        </el-col>
      </el-row>
      <el-row class="cost">
        <el-col class="icon-label">
          <el-icon>
            <Money />
          </el-icon>
          <span class="label">
            {{ `${labelMsg("schedule.cost")}:` }}
          </span>
          <span class="value">
            {{ $won(schedule.cost) }}
          </span>
        </el-col>
      </el-row>
      <el-row class="city">
        <el-col class="icon-label">
          <el-icon>
            <Location />
          </el-icon>
          <span class="label">
            {{ `${labelMsg("schedule.location")}:` }}
          </span>
          <span class="value">
            {{ categoryMsg("city.name", schedule.city.code) }}
          </span>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="count">
        <el-col :span="2" class="icon-value">
          <el-icon>
            <View />
          </el-icon>
          <span class="value">
            {{ $count(schedule.numberOfHits) }}
          </span>
        </el-col>
        <el-col :span="2" v-if="showLikes" class="icon-value">
          <i class="bi bi-hand-thumbs-up"></i>
          <span class="value">
            {{ $count(schedule.numberOfLikes) }}
          </span>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.title-info {
  width: 82%;

  .details {
    margin-top: 8px;
    display: flex;
    flex-direction: column;
    justify-content: start;

    .el-row {
      display: flex;
      justify-content: start;
      align-items: center;
      margin-bottom: 10px;

      .icon-label {
        display: flex;
        justify-content: start;
        align-items: center;

        .label {
          margin: 0 5px;
        }
      }

      .icon-value {
        display: flex;
        justify-content: start;
        align-items: center;

        .value {
          margin: 0 5px;
        }
      }
    }
  }
}
</style>
