<script lang="ts" setup>
import { computed, ref } from "vue";
import type { ScheduleContentType } from "@/modules/types/schedule/ScheduleType";
import { useScheduleStore } from "@/stores/schedule";

const store = useScheduleStore();
const props = defineProps({
  index: {
    type: Number,
    required: true,
  },
});
const scheduleContents = computed(() => store.scheduleContents(props.index));
const attrs = computed(() =>
  scheduleContents.value.map((content: ScheduleContentType) => ({
    date: content.date,
    popover: {
      label: content.description,
    },
    highlight: "teal",
    customData: scheduleContents.value,
  }))
);
</script>

<template>
  <v-calendar
    :attributes="attrs"
    :max-date="'2022-08-12'"
    :min-date="'2022-08-02'"
    trim-weeks
  >
    <template #day-popover="{ day, attributes }">
      <el-row>
        {{ day.ariaLabel }}
      </el-row>
      <el-row class="mt-2 mx-1">
        <el-col>
          <el-timeline class="px-2">
            <el-timeline-item
              v-for="({ customData }, index) in attributes"
              :key="index"
              :hollow="true"
              class="pb-1"
              color="#004a55"
            >
              <span class="p-0 m-0" style="color: white">
                {{ customData.description }}
              </span>
            </el-timeline-item>
          </el-timeline>
        </el-col>
      </el-row>
    </template>
  </v-calendar>
</template>

<style lang="scss" scoped></style>
