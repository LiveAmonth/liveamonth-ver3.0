<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/detail/ScheduleCalendar.vue";
import ScheduleForm from "@/components/schedule/ScheduleForm.vue";
import OpenModal from "@/components/schedule/member/OpenModal.vue";
import { Plus } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

const props = defineProps({
  loginId: {
    type: String,
    required: true,
  },
  scheduleId: {
    type: String || Number,
    required: false,
  },
});
const { mySchedules, getMySchedules, getScheduleContents, deleteSchedule } =
  useSchedule();
const { openConfirmMessageBox, openMessageBox } = useMessageBox();
const { t } = useI18n();
const selectedId = ref<number>();
const currSchedule = ref<ScheduleCardType | null>();
const initDate = ref<string>("");
const calendarKey = ref(0);
const modal = ref(false);

onMounted(async () => {
  await getMySchedules(props.loginId).then(async () => {
    selectedId.value = mySchedules.value[0].id;
    await changeSchedule();
  });
});

const findSchedule = (): ScheduleCardType | undefined => {
  return mySchedules.value.find((value) => value.id === selectedId.value);
};

const changeSchedule = async () => {
  currSchedule.value = findSchedule();
  if (selectedId.value != null) {
    await getScheduleContents(selectedId.value).then(() => {
      initDate.value = String(currSchedule.value?.period.startDate);
      calendarKey.value += 1;
    });
  }
};

const submitForm = async (isEdit = false) => {
  await getMySchedules(props.loginId).then(() => {
    if (!isEdit) {
      selectedId.value = mySchedules.value[0].id;
      changeSchedule();
      modal.value = false;
    }
  });
};

const deleteBtn = async () => {
  await openConfirmMessageBox(
    t("form.message.schedule.delete.title"),
    t("form.message.schedule.delete.message")
  ).then(async () => {
    if (selectedId.value != null) {
      deleteSchedule(selectedId.value).then(() => {
        getMySchedules(props.loginId).then(() => {
          selectedId.value = mySchedules.value[0].id;
          changeSchedule();
        });
      });
    } else {
      await openMessageBox(t("form.message.schedule.delete.exception"));
    }
  });
};
</script>

<template>
  <el-row v-if="currSchedule" class="mb-5">
    <el-col>
      <el-row :gutter="10">
        <el-col :span="18">
          <div class="d-flex justify-content-between mt-3 mb-3">
            <TitleSlot>내 스케줄 관리</TitleSlot>
            <div class="select-content d-flex justify-content-end">
              <el-select
                v-model="selectedId"
                :placeholder="$t('common.select')"
              >
                <template v-for="val in mySchedules" :key="val.id">
                  <el-option :label="val.title" :value="val.id" />
                </template>
              </el-select>
              <el-button class="ms-2" @click="changeSchedule">이동</el-button>
            </div>
          </div>
          <ScheduleCalendar
            :key="calendarKey"
            v-if="initDate"
            :editable="false"
            :init-date="initDate"
            :manage-state="false"
            @select-content="console.log('선택..')"
          />
        </el-col>
        <el-col :span="6">
          <div class="d-flex justify-content-end mt-3 mb-4">
            <el-button
              class="d-flex justify-content-between"
              @click="modal = true"
              size="large"
              text
            >
              <el-icon class="me-1"><Plus /></el-icon>
              {{ $t("schedule.form.main.add") }}
            </el-button>
          </div>
          <ScheduleForm
            :schedule="currSchedule"
            @submit="submitForm"
            @delete-schedule="deleteBtn"
          >
            <template v-slot:title>
              {{ $t("schedule.title.schedule") }}
            </template>
          </ScheduleForm>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <OpenModal @close="modal = false" v-if="modal">
    <ScheduleForm @submit="submitForm">
      <template v-slot:title>
        {{ $t("schedule.form.main.add") }}
      </template>
    </ScheduleForm>
  </OpenModal>
</template>

<style lang="scss" scoped>
.layout-container-demo .el-header {
  position: relative;
}

.layout-container-demo .el-aside {
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.el-collapse {
  border: none;
}
</style>
