<script setup lang="ts">
import { ref } from "vue";
import SimpleScheduleCard from "@/components/schedule/SimpleScheduleCard.vue";
import { useSchedule } from "@/composables/schedule";
import { useCalendarEvent } from "@/composables/calendarEvent";

defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});
const { currScheduleContents } = useSchedule();
const { contentCollapse } = useCalendarEvent();
</script>
<template>
  <SimpleScheduleCard :id="id" />
  <el-collapse v-model="contentCollapse" class="search">
    <template v-for="(content, idx) in currScheduleContents" :key="idx">
      <el-collapse-item :name="content.id">
        <template #title>
          <el-icon class="me-1">
            <Calendar />
          </el-icon>
          {{ content.title }}
        </template>
        {{ content.content }}
        {{ content.cost }}
        {{ content.timePeriod.startDateTime }}
        {{ content.timePeriod.endDateTime }}
      </el-collapse-item>
    </template>
  </el-collapse>
</template>
<style scoped lang="scss"></style>
