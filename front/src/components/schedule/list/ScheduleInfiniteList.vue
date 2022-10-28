<script lang="ts" setup>
import ScheduleInfoCard from "@/components/schedule/card/ScheduleInfoCard.vue";
import SimpleCalendar from "@/components/schedule/calendar/SimpleCalendar.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import { Right } from "@element-plus/icons-vue";
import { computed, ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMember } from "@/composables/member/member";
import { useMessageBox } from "@/composables/common/messageBox";
import type {
  MyScheduleType,
  ScheduleCardType,
} from "@/modules/types/schedule/ScheduleTypes";

const props = defineProps({
  initialCount: {
    type: Number,
    required: true,
  },
  isMyPage: {
    type: Boolean,
    required: false,
    default: false,
  },
  isMain: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emits = defineEmits(["refresh"]);

const { simpleProfile } = useMember();
const {
  hasMySchedules,
  hasFollowedSchedules,
  mySchedules,
  followedSchedules,
  getInfiniteSchedules,
  deleteSchedule,
} = useSchedule();
const { labelMsg, buttonMsg } = useMessageBox();

const size = ref<number>(10);
const schedules = computed((): ScheduleCardType[] | MyScheduleType[] =>
  props.isMyPage ? mySchedules.value : followedSchedules.value
);
const count = ref<number>(
  schedules.value.length < props.initialCount
    ? schedules.value.length
    : props.initialCount
);
const loading = ref<boolean>(false);
const noMore = computed(() =>
  props.isMyPage
    ? simpleProfile.value.numberOfSchedules
    : count.value >= 30 || schedules.value.length % 10 != 0
);
const disabled = computed(() => loading.value || noMore.value);

const hasSchedules = computed(() =>
  props.isMyPage ? hasMySchedules.value : hasFollowedSchedules.value
);

const load = async () => {
  loading.value = true;
  await getInfiniteSchedules(
    simpleProfile.value.loginId,
    size.value,
    schedules.value[count.value - 1].id,
    props.isMyPage
  );
  setTimeout(() => {
    count.value =
      schedules.value.length - count.value < size.value
        ? schedules.value.length
        : count.value + size.value;
    loading.value = false;
  }, 500);
};

const handleDelete = async (scheduleId: number) => {
  await deleteSchedule(scheduleId);
  await getInfiniteSchedules(
    simpleProfile.value.loginId,
    props.initialCount,
    null,
    props.isMyPage
  );
  simpleProfile.value.numberOfSchedules--;
  count.value--;
  loading.value = false;
  emits("refresh");
};
</script>

<template>
  <div v-if="hasSchedules" class="infinite-list-wrapper" style="overflow: auto">
    <ul
      v-infinite-scroll="load"
      :infinite-scroll-disabled="disabled"
      class="list"
    >
      <li
        v-for="schedule in schedules"
        :key="schedule.id"
        class="list-item py-4"
      >
        <el-row v-if="schedule" class="item d-flex justify-content-center">
          <SimpleCalendar class="me-3" :period="schedule.period" />
          <el-col :span="16" class="schedule-list-card">
            <ScheduleInfoCard
              :is-my-page="isMyPage"
              :login-id="simpleProfile.loginId"
              :schedule="schedule"
              :is-main="isMain"
              @delete-schedule="handleDelete(schedule.id)"
            />
          </el-col>
        </el-row>
      </li>
    </ul>
  </div>
  <div v-else class="empty-post">
    <SmallTitleSlot
      class="d-flex justify-content-center"
      :title="labelMsg('schedule.empty')"
      :title-line="false"
    />
    <LinkSlot
      class="link"
      :link="`${
        isMyPage ? `/my-schedule/${simpleProfile.loginId}` : '/schedules'
      }`"
    >
      <span>
        {{ buttonMsg(`${isMyPage ? "schedule.go" : "schedule.goOther"}`) }}
      </span>
      <el-icon>
        <Right />
      </el-icon>
    </LinkSlot>
  </div>
</template>

<style lang="scss" scoped>
.infinite-list-wrapper {
  height: 800px;
  text-align: center;

  .list {
    padding: 0;
    margin: 0;
    list-style: none;
  }

  .list-item {
    align-items: center;
    height: 270px;

    & + & {
      margin-top: 10px;
    }

    .schedule-list-card {
      display: flex;
      justify-content: center;
      flex-direction: column;

      .reply {
        margin-top: 1.3rem;
        text-align: left;
      }
    }
  }
}

.empty-post {
  display: flex;
  justify-content: center;
  flex-direction: column;
  color: #808080;
  margin-top: 15px;

  .link {
    display: flex;
    justify-content: center;
    margin-top: 5px;
    color: #016d7d;
  }
}
</style>
