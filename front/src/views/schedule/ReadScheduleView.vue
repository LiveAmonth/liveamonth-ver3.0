<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/ScheduleCalendar.vue";
import ScheduleInformation from "@/components/schedule/ScheduleInformation.vue";
import ReplyComponent from "@/components/reply/ReplyComponent.vue";
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
const { contentCollapse, setContentCollapse } = useCalendarEvent();
const scheduleCard = ref<ScheduleCardType>(getOtherSchedule(Number(props.id)));

// const authStore = useAuthStore();
// const loggedIn = computed(() => authStore.loggedIn);
// const isLoggedInMemberSchedule = () => {
//   return (
//     loggedIn.value &&
//     memberStore.memberProfile.loginId === scheduleCard.value.profile.loginId
//   );
// };
onMounted(async () => {
  await getScheduleContents(Number(props.id));
  await setContentCollapse();
});

const changeCollapse = (id: number) => {
  setContentCollapse(id);
};
</script>

<template>
  <el-row class="mb-5" v-if="!isPending">
    <el-col>
      <TitleSlot>{{ scheduleCard.title }}</TitleSlot>
      <el-row :gutter="10">
        <el-col :span="18">
          <ScheduleCalendar
            @select-content="changeCollapse"
            :manage-state="false"
            :editable="false"
          />
        </el-col>
        <el-col :span="6">
          <ScheduleInformation :id="id" />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <el-row>
    <TitleSlot>댓글</TitleSlot>
    <ReplyComponent />
  </el-row>
</template>

<style scoped lang="scss">
.el-calendar-day {
  padding: 0;
}
</style>
