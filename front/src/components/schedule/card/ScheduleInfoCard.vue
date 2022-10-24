<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleInfoSlot from "@/components/schedule/slot/ScheduleInfoSlot.vue";
import ProfileCard from "@/components/common/ProfileCard.vue";
import { Right, Close } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMessageBox } from "@/composables/common/messageBox";
import type { PropType } from "vue";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleTypes";

const props = defineProps({
  schedule: {
    type: Object as PropType<ScheduleCardType>,
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
const emit = defineEmits(["deleteSchedule"]);

const router = useRouter();
const {
  error,
  hasCurrentSchedule,
  hasEditedSchedule,
  setCurrentSchedule,
  setEditedSchedule,
} = useSchedule();
const { buttonMsg, resultMsg, openConfirmMessageBox } = useMessageBox();

const goSchedule = async () => {
  if (props.isMyPage) {
    await setEditedSchedule(props.schedule.id);
    if (!error.value && hasEditedSchedule.value) {
      await router.push({
        name: "my-schedule",
        params: {
          loginId: props.loginId,
        },
      });
    }
  } else {
    await setCurrentSchedule(props.schedule.id);
    if (!error.value && hasCurrentSchedule.value) {
      await router.push({
        name: "read-schedule",
        params: {
          id: props.schedule.id,
        },
      });
    }
  }
};

const handleDelete = async () => {
  await openConfirmMessageBox(
    resultMsg("schedule.delete.title"),
    resultMsg("schedule.delete.message")
  ).then(() => {
    emit("deleteSchedule");
  });
};
</script>

<template>
  <el-card class="information" :body-style="{ padding: '0 20px 0 0' }">
    <el-row :gutter="10" class="profile-title d-flex justify-content-between">
      <el-col :span="8" v-if="!isMyPage">
        <div class="profile">
          <ProfileCard :profile="schedule.profile" :is-list="true" />
        </div>
      </el-col>
      <el-col
        :span="16"
        class="d-flex justify-content-center align-items-center"
      >
        <ScheduleInfoSlot
          :schedule="schedule"
          :font-size="isMyPage ? 1.0 : 1.0"
          :show-likes="true"
        >
          <template v-slot:title>
            <SmallTitleSlot
              class="slot"
              @click="goSchedule"
              style="cursor: pointer"
              :title="`${schedule.title}`"
            >
              {{ schedule.title }}
              <span style="font-size: 1.1rem; font-weight: 400">
                ({{ schedule.numberOfComments }})
              </span>
            </SmallTitleSlot>
          </template>
        </ScheduleInfoSlot>
      </el-col>
      <div v-if="isMyPage" class="delete" @click="handleDelete">
        {{ buttonMsg("delete") }}
        <el-icon>
          <Close />
        </el-icon>
      </div>
    </el-row>
  </el-card>
  <div v-if="isMyPage" class="manage mt-3" @click="goSchedule">
    {{ buttonMsg("schedule.manage") }}
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
  margin-top: 15px;
}

.manage {
  display: flex;
  justify-content: end;
  cursor: pointer;
}
</style>
