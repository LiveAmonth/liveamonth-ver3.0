<script setup lang="ts">
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import { useSchedule } from "@/composables/schedule";
import { computed, ref } from "vue";
import { useMember } from "@/composables/member";

const props = defineProps({
  initialCount: {
    type: Number,
    required: true,
  },
});
const emits = defineEmits(["refresh"]);
const { memberProfile } = useMember();
const { mySchedules, getAdditionalMySchedules, deleteSchedule } = useSchedule();

const size = ref<number>(2);
const count = ref<number>(props.initialCount);
const loading = ref<boolean>(false);

const noMore = computed(
  () => count.value >= memberProfile.value.numberOfSchedules
);
const disabled = computed(() => loading.value || noMore.value);

const load = async () => {
  loading.value = true;
  await getAdditionalMySchedules(
    memberProfile.value.loginId,
    size.value,
    mySchedules.value[count.value - 1].id
  );
  setTimeout(() => {
    count.value += size.value;
    loading.value = false;
  }, 2000);
};

const deleteScheduleBtn = async (scheduleId: number) => {
  await deleteSchedule(scheduleId);
  await getAdditionalMySchedules(
    memberProfile.value.loginId,
    props.initialCount,
    null
  );
  memberProfile.value.numberOfSchedules--;
  count.value--;
  loading.value = false;
  emits("refresh");
};
</script>

<template>
  <div class="infinite-list-wrapper" style="overflow: auto">
    <ul
      v-infinite-scroll="load"
      class="list"
      :infinite-scroll-disabled="disabled"
    >
      <li v-for="i in count" :key="i" class="list-item">
        <el-row class="d-flex justify-content-center" v-if="mySchedules[i - 1]">
          <el-col class="me-3" :lg="8" :md="8" :sm="8" :xl="6" :xs="8">
            <SimpleCalendar :period="mySchedules[i - 1].period" />
          </el-col>
          <el-col :lg="14" :md="14" :sm="14" :xl="16" :xs="14">
            <ScheduleInfoCard
              :login-id="memberProfile.loginId"
              :schedule="mySchedules[i - 1]"
              :is-my-page="true"
              @delete-schedule="deleteScheduleBtn"
            />
          </el-col>
        </el-row>
      </li>
    </ul>
    <p v-if="loading">Loading...</p>
    <p v-if="noMore">No more</p>
  </div>
</template>

<style scoped lang="scss">
.infinite-list-wrapper {
  height: 800px;
  text-align: center;
}
.infinite-list-wrapper .list {
  padding: 0;
  margin: 0;
  list-style: none;
}

.infinite-list-wrapper .list-item {
  align-items: center;
  height: 270px;
}

.infinite-list-wrapper .list-item + .list-item {
  margin-top: 10px;
}
</style>
