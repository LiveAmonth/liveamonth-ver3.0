<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/detail/ScheduleCalendar.vue";
import ScheduleDetail from "@/components/schedule/detail/ScheduleDetail.vue";
import CommentComponent from "@/components/comment/CommentComponent.vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import { useCalendarEvent } from "@/composables/calendarEvent";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});
const { isPending, getOtherSchedule, getScheduleContents } = useSchedule();
const { setContentCollapse } = useCalendarEvent();
const scheduleCard = ref<ScheduleCardType>(getOtherSchedule(Number(props.id)));

onMounted(async () => {
  await getScheduleContents(Number(props.id));
});

const changeCollapse = (id: number) => {
  setContentCollapse(id);
};
</script>

<template>
  <el-row v-if="!isPending" class="mb-5">
    <el-col>
      <TitleSlot>{{ scheduleCard.title }}</TitleSlot>
      <el-row :gutter="10">
        <el-col :span="18">
          <ScheduleCalendar
            :editable="false"
            :manage-state="false"
            @select-content="changeCollapse"
          />
        </el-col>
        <el-col :span="6">
          <ScheduleDetail :id="id" />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <el-divider />
  <el-row>
    <el-col>
      <CommentComponent :id="id" :type="'SCHEDULE'" />
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.el-calendar-day {
  padding: 0;
}
</style>
