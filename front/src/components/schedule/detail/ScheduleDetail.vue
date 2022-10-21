<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import {
  Reading,
  Paperclip,
  Money,
  Clock,
  ArrowDown,
  ArrowUp,
} from "@element-plus/icons-vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMessageBox } from "@/composables/common/messageBox";
import { computed } from "vue";

const { currScheduleContents, contentCollapse, handleContentCollapse } =
  useSchedule();
const { titleMsg, labelMsg, buttonMsg } = useMessageBox();
const isOpen = computed(
  () => contentCollapse.value.length === currScheduleContents.value.length
);
const handleCollapse = () => {
  handleContentCollapse(!isOpen.value);
};
</script>
<template>
  <el-card class="content">
    <div class="d-flex justify-content-between">
      <SmallTitleSlot :title="titleMsg('schedule.content.info')" class="mb-4" />
      <el-button text size="small" @click="handleCollapse">
        {{ buttonMsg(`schedule.collapse.${isOpen ? "close" : "open"}`) }}
        <el-icon class="ms-1">
          <ArrowUp v-if="isOpen" />
          <ArrowDown v-else />
        </el-icon>
      </el-button>
    </div>
    <el-collapse v-model="contentCollapse" class="search">
      <template v-for="(content, idx) in currScheduleContents" :key="idx">
        <el-collapse-item :name="content.id">
          <template #title>
            <el-icon class="me-1">
              <Paperclip />
            </el-icon>
            {{ content.title }}
          </template>
          <el-descriptions :column="2" border direction="vertical" size="small">
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Reading />
                  </el-icon>
                  {{ labelMsg("content") }}
                </div>
              </template>
              {{ content.content }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Money />
                  </el-icon>
                  {{ labelMsg("schedule.content.cost") }}
                </div>
              </template>
              {{ $won(content.cost) }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Clock />
                  </el-icon>
                  {{ labelMsg("schedule.content.start") }}
                </div>
              </template>
              {{ content.timePeriod.startDateTime }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon>
                    <Clock />
                  </el-icon>
                  {{ labelMsg("schedule.content.end") }}
                </div>
              </template>
              {{ content.timePeriod.endDateTime }}
            </el-descriptions-item>
          </el-descriptions>
        </el-collapse-item>
      </template>
    </el-collapse>
  </el-card>
</template>
<style lang="scss" scoped></style>
