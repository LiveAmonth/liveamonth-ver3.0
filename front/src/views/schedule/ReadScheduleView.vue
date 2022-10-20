<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/calendar/ScheduleCalendar.vue";
import ScheduleDetail from "@/components/schedule/detail/ScheduleDetail.vue";
import CommentComponent from "@/components/comment/CommentComponent.vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useCalendarEvent } from "@/composables/schedule/calendarEvent";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});
const { currentSchedule, getScheduleContents } = useSchedule();
const { setContentCollapse } = useCalendarEvent();
const commentKey = ref<number>(0);

onMounted(async () => {
  await getScheduleContents(Number(props.id));
});

const changeCollapse = (id: number) => {
  setContentCollapse(id);
};
</script>

<template>
  <el-row v-if="currentSchedule.id" class="mb-5">
    <el-col>
      <TitleSlot :title="currentSchedule.title" />
      <el-row :gutter="5">
        <el-col :span="18">
          <ScheduleCalendar
            :init-period="currentSchedule.period"
            @select-content="changeCollapse"
          />
        </el-col>
        <el-col :span="6">
          <ScheduleDetail />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <el-divider />
  <el-row v-if="currentSchedule.id">
    <el-col class="d-flex justify-content-center">
      <CommentComponent
        :key="commentKey"
        :content-id="Number(id)"
        :type="'schedule'"
        :writer="currentSchedule.profile.nickname"
        @refresh="commentKey++"
      />
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.el-calendar-day {
  padding: 0;
}
</style>
