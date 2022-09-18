<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import PopoverProfileSlot from "@/components/common/PopoverProfileSlot.vue";
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import { Location, Money, View, Right, Close } from "@element-plus/icons-vue";
import { useType } from "@/composables/type";
import type { PropType } from "vue";
import type { SearchSortFormType } from "@/modules/types/common/SearchType";
import type {
  MyScheduleCardType,
  ScheduleCardType,
} from "@/modules/types/schedule/ScheduleType";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";

const props = defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType | MyScheduleCardType>,
    required: true,
  },
  isMyPage: {
    type: Boolean,
    required: false,
    default: false,
  },
  loginId: {
    type: String,
    required: false,
  },
});
const emit = defineEmits(["goToMemberSchedules", "deleteSchedule"]);

const router = useRouter();
const { scheduleSearchType, scheduleSortType } = useType();
const { setSchedule } = useSchedule();
const { openConfirmMessageBox } = useMessageBox();
const { t } = useI18n();

const otherSchedule = ref<ScheduleCardType>(props.schedule as ScheduleCardType);
const goToMemberSchedules = () => {
  const request: SearchSortFormType = {
    searchType: scheduleSearchType.value[0].code,
    searchInput: otherSchedule.value.profile.nickname,
    filterType: null,
    filterInput: null,
    sortType: scheduleSortType.value[0].title,
  };
  emit("goToMemberSchedules", request);
};

const goSchedule = async () => {
  if (props.isMyPage) {
    await setSchedule(props.schedule.id).then(() => {
      router.push({
        name: "my-schedule",
        params: {
          loginId: props.loginId,
        },
      });
    });
  } else {
    await router.push({
      name: "read-schedule",
      params: {
        id: props.schedule.id,
      },
    });
  }
};

const deleteScheduleBtn = async () => {
  await openConfirmMessageBox(
    t("form.message.schedule.delete.title"),
    t("form.message.schedule.delete.message")
  ).then(() => {
    emit("deleteSchedule", props.schedule.id);
  });
};
</script>

<template>
  <el-card class="information" :body-style="{ paddingRight: 0 }">
    <div class="profile-title d-flex justify-content-between">
      <div class="profile" v-if="!isMyPage">
        <PopoverProfileSlot :schedule="otherSchedule">
          <a class="mention" @click="goToMemberSchedules">
            @ {{ $t("schedule.popover.link") }}
          </a>
        </PopoverProfileSlot>
        <div class="nickname mt-2">
          {{ schedule.profile.nickname }}
        </div>
      </div>
      <ScheduleInfoSlot
        :schedule="schedule"
        :font-size="isMyPage ? 1.0 : 0.9"
        :show-likes="true"
        :is-my-page="isMyPage"
      >
        <template v-slot:title>
          <SmallTitleSlot
            class="slot"
            @click="goSchedule"
            style="cursor: pointer"
          >
            {{ schedule.title }}
          </SmallTitleSlot>
        </template>
        <template v-slot:period-title>
          <el-tooltip
            :content="$t('schedule.tooltip.date')"
            placement="left-start"
          >
            <el-icon>
              <Calendar />
            </el-icon>
          </el-tooltip>
        </template>
        <template v-slot:cost-title>
          <el-tooltip
            :content="$t('schedule.tooltip.cost')"
            placement="left-start"
          >
            <el-icon>
              <Money />
            </el-icon>
          </el-tooltip>
        </template>
        <template v-slot:location-title>
          <el-tooltip
            :content="$t('schedule.tooltip.location')"
            placement="left-start"
          >
            <el-icon>
              <Location />
            </el-icon>
          </el-tooltip>
        </template>
        <template v-slot:view-title>
          <el-tooltip
            :content="$t('schedule.tooltip.view')"
            placement="bottom-end"
          >
            <el-icon class="me-2">
              <View />
            </el-icon>
          </el-tooltip>
        </template>
        <template v-slot:like-title>
          <el-tooltip :content="$t('schedule.tooltip.like')" placement="bottom">
            <i class="bi bi-hand-thumbs-up"></i>
          </el-tooltip>
        </template>
      </ScheduleInfoSlot>
      <div v-if="isMyPage" class="delete" @click="deleteScheduleBtn">
        삭제
        <el-icon>
          <Close />
        </el-icon>
      </div>
    </div>
  </el-card>
  <div v-if="isMyPage" class="manage mt-3" @click="goSchedule">
    스케줄 관리
    <el-icon>
      <Right />
    </el-icon>
  </div>
</template>
<style scoped lang="scss">
.nickname {
  font-size: 0.9rem;
}

span {
  font-size: 0.78rem;
}

.el-icon {
  padding-bottom: 0.1rem;
}

.delete {
  display: flex;
  justify-content: end;
  cursor: pointer;
  margin-right: 10px;
}

.manage {
  display: flex;
  justify-content: end;
  cursor: pointer;
}
</style>
