<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/ScheduleCalendar.vue";
import ScheduleInformation from "@/components/schedule/ScheduleInformation.vue";
import { computed, onMounted, ref } from "vue";
import { useScheduleStore } from "@/stores/schedule";
import { useSchedule } from "@/composables/schedule";
import { useMemberStore } from "@/stores/member";
import { useAuthStore } from "@/stores/auth";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import { useCalendarEvent } from "@/composables/calendarEvent";

const props = defineProps({
  id: {
    type: [String || Number],
    required: true,
  },
});
const { isPending, getOtherSchedule, getScheduleContents } = useSchedule();
const { setContentCollapse } = useCalendarEvent();
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
</script>

<template>
  <el-row v-if="!isPending">
    <el-col>
      <TitleSlot>{{ scheduleCard.title }}</TitleSlot>
      <el-row :gutter="10">
        <el-col :span="18">
          <ScheduleCalendar :manage-state="false" :editable="false" />
        </el-col>
        <el-col :span="6">
          <ScheduleInformation :id="id" />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.el-calendar-day {
  padding: 0;
}
</style>
