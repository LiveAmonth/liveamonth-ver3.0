<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/calendar/ScheduleCalendar.vue";
import EditContentForm from "@/components/schedule/form/EditContentForm.vue";
import OpenModal from "@/components/common/OpenModal.vue";
import ScheduleForm from "@/components/schedule/form/ScheduleForm.vue";
import AddContentForm from "@/components/schedule/form/AddContentForm.vue";
import { Plus } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import { useCalendarEvent } from "@/composables/calendarEvent";

const props = defineProps({
  loginId: {
    type: String,
    required: true,
  },
});
const {
  mySchedules,
  currSchedule,
  getMySchedules,
  getScheduleContents,
  deleteSchedule,
  deleteContent,
  setSchedule,
} = useSchedule();
const { selectedContent, resetContent } = useCalendarEvent();
const { openConfirmMessageBox, openMessageBox } = useMessageBox();
const { t } = useI18n();

const selectedId = ref<number>(
  currSchedule.value.id && currSchedule.value.profile.loginId === props.loginId
    ? currSchedule.value.id
    : mySchedules.value[0].id
);
const initDate = ref<string>("");
const calendarKey = ref(0);
const scheduleModal = ref<boolean>(false);
const contentModal = ref<boolean>(false);
const defaultContentDate = ref<string>("");

onMounted(async () => {
  await getMySchedules(props.loginId);
  await changeSchedule();
});

const changeSchedule = async () => {
  await setSchedule(selectedId.value);
  await getScheduleContents(selectedId.value).then(() => {
    initDate.value = String(currSchedule.value?.period.startDate);
    calendarKey.value += 1;
    resetContent();
  });
  defaultContentDate.value = currSchedule.value.period.startDate;
};

const submitScheduleForm = async (isEdit = false) => {
  await getMySchedules(props.loginId).then(() => {
    if (!isEdit) {
      selectedId.value = mySchedules.value[0].id;
      changeSchedule();
    }
  });
};

const deleteScheduleBtn = async () => {
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

const openContentModal = () => {
  defaultContentDate.value = currSchedule.value.period.startDate;
  contentModal.value = true;
};

const submitContentForm = async (isEdit = false) => {
  await getScheduleContents(selectedId.value).then(() => {
    calendarKey.value += 1;
    if (!isEdit) {
      resetContent();
    }
  });
};

const deleteContentBtn = async (contentId: number) => {
  await openConfirmMessageBox(
    t("form.message.content.delete.title"),
    t("form.message.content.delete.message")
  ).then(async () => {
    if (selectedId.value != null) {
      await deleteContent(contentId).then(async () => {
        await getScheduleContents(selectedId.value).then(() => {
          calendarKey.value += 1;
          resetContent();
        });
      });
    } else {
      await openMessageBox(t("form.message.content.delete.exception"));
    }
  });
};
</script>

<template>
  <div v-if="currSchedule">
    <el-row class="mb-5">
      <el-col>
        <el-row :gutter="10">
          <el-col :span="18">
            <div class="d-flex justify-content-between mt-3 mb-3">
              <TitleSlot>내 스케줄 관리</TitleSlot>
              <div class="select-content d-flex justify-content-end">
                <el-select
                  v-model="selectedId"
                  :placeholder="$t('common.select')"
                  @change="changeSchedule"
                >
                  <template v-for="val in mySchedules" :key="val.id">
                    <el-option :label="val.title" :value="val.id" />
                  </template>
                </el-select>
              </div>
            </div>
            <ScheduleCalendar
              :key="calendarKey"
              v-if="initDate"
              :schedule-id="selectedId"
              :editable="true"
              :init-date="initDate"
              :manage-state="true"
              v-model:content-modal="contentModal"
              v-model:default-date="defaultContentDate"
            />
          </el-col>
          <el-col :span="6" class="mt-4">
            <div class="schedule-form">
              <div class="d-flex justify-content-end mt-3">
                <el-button
                  class="d-flex justify-content-between"
                  @click="scheduleModal = true"
                  size="large"
                  text
                >
                  <el-icon class="me-1">
                    <Plus />
                  </el-icon>
                  {{ $t("schedule.form.main.add") }}
                </el-button>
              </div>
              <ScheduleForm
                :schedule="currSchedule"
                @submit="submitScheduleForm"
                @delete-schedule="deleteScheduleBtn"
              >
                <template v-slot:title>
                  {{ $t("schedule.title.schedule") }}
                </template>
              </ScheduleForm>
            </div>
            <div class="content-form">
              <div class="d-flex justify-content-end mt-3">
                <el-button
                  class="d-flex justify-content-between"
                  @click="openContentModal"
                  size="large"
                  text
                >
                  <el-icon class="me-1">
                    <Plus />
                  </el-icon>
                  {{ $t("schedule.form.content.add") }}
                </el-button>
              </div>
              <EditContentForm
                :schedule-id="selectedId"
                :content="selectedContent"
                :period="currSchedule.period"
                @submit="submitContentForm"
                @delete-content="deleteContentBtn"
              />
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <OpenModal @close="scheduleModal = false" v-if="scheduleModal">
      <ScheduleForm @submit="submitScheduleForm">
        <template v-slot:title>
          {{ $t("schedule.form.main.add") }}
        </template>
      </ScheduleForm>
    </OpenModal>
    <OpenModal @close="contentModal = false" v-if="contentModal">
      <AddContentForm
        :schedule-id="selectedId"
        :period="currSchedule.period"
        :default-date="defaultContentDate"
        @submit="submitContentForm"
      />
    </OpenModal>
  </div>
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
