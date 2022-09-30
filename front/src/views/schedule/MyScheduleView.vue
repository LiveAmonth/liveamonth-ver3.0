<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/calendar/ScheduleCalendar.vue";
import EditContentForm from "@/components/schedule/form/EditContentForm.vue";
import OpenModal from "@/components/common/OpenModal.vue";
import ScheduleForm from "@/components/schedule/form/ScheduleForm.vue";
import AddContentForm from "@/components/schedule/form/AddContentForm.vue";
import { Plus } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMessageBox } from "@/composables/common/messageBox";
import { useI18n } from "vue-i18n";
import { useCalendarEvent } from "@/composables/schedule/calendarEvent";

const props = defineProps({
  loginId: {
    type: String,
    required: true,
  },
});

const {
  mySchedules,
  editedSchedule,
  getMySchedules,
  getScheduleContents,
  deleteSchedule,
  deleteContent,
  getInitialSelectedId,
  setSchedule,
} = useSchedule();
const { resetContent } = useCalendarEvent();
const { openConfirmMessageBox, openMessageBox } = useMessageBox();
const { t } = useI18n();

const isPending = ref<boolean>(true);
const selectedId = ref<string | number>("");
const initDate = ref<string>("");
const calendarKey = ref(0);
const scheduleModal = ref<boolean>(false);
const contentModal = ref<boolean>(false);
const defaultContentDate = ref<string>("");

onMounted(async () => {
  await getMySchedules(props.loginId).then(() => {
    selectedId.value = getInitialSelectedId();
  });
  await changeSchedule().then(() => {
    isPending.value = false;
  });
});

const changeSchedule = async () => {
  if (selectedId.value) {
    await setSchedule(Number(selectedId.value));
    await getScheduleContents(Number(selectedId.value)).then(() => {
      initDate.value = editedSchedule.value.period.startDate;
      calendarKey.value += 1;
    });
    await resetContent();
    defaultContentDate.value = editedSchedule.value.period.startDate;
  }
};

const submitScheduleForm = async (isEdit = false) => {
  await getMySchedules(props.loginId).then(() => {
    if (!isEdit) {
      selectedId.value = mySchedules.value[0].id;
    }
  });
  await changeSchedule().then(() => {
    scheduleModal.value = false;
  });
};

const deleteScheduleBtn = async () => {
  await openConfirmMessageBox(
    t("form.message.schedule.delete.title"),
    t("form.message.schedule.delete.message")
  ).then(async () => {
    if (editedSchedule.value.id != null) {
      await deleteSchedule(editedSchedule.value.id);
      await getMySchedules(props.loginId);
      selectedId.value = await getInitialSelectedId();
      await changeSchedule();
    } else {
      await openMessageBox(t("form.message.schedule.delete.exception"));
    }
  });
};

const openContentModal = () => {
  defaultContentDate.value = editedSchedule.value.period.startDate;
  contentModal.value = true;
};

const submitContentForm = async (isEdit = false) => {
  await getScheduleContents(editedSchedule.value.id).then(() => {
    calendarKey.value += 1;
    if (!isEdit) {
      resetContent();
    }
  });
  contentModal.value = false;
};

const deleteContentBtn = async (contentId: number) => {
  await openConfirmMessageBox(
    t("form.message.content.delete.title"),
    t("form.message.content.delete.message")
  ).then(async () => {
    await deleteContent(contentId).then(async () => {
      await getScheduleContents(editedSchedule.value.id).then(() => {
        calendarKey.value += 1;
        resetContent();
      });
    });
  });
};
</script>

<template>
  <el-row class="mb-5">
    <el-col>
      <el-row v-if="!isPending" :gutter="10">
        <el-col :span="18">
          <div class="d-flex justify-content-between mt-3 mb-3">
            <TitleSlot>{{ $t("schedule.title.manage") }}</TitleSlot>
            <div class="select-content d-flex justify-content-end">
              <el-select
                v-model="selectedId"
                :placeholder="$t('common.select')"
                @change="changeSchedule"
              >
                <el-option
                  v-for="val in mySchedules"
                  :key="val.id"
                  :label="val.title"
                  :value="val.id"
                />
              </el-select>
            </div>
          </div>
          <ScheduleCalendar
            :key="calendarKey"
            :editable="true"
            :init-date="initDate"
            :is-basic="!selectedId"
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
              :selected-id="Number(selectedId)"
              @submit="submitScheduleForm"
              @delete-schedule="deleteScheduleBtn"
            >
              <template v-slot:title>
                {{ $t("schedule.title.schedule") }}
              </template>
            </ScheduleForm>
          </div>
          <div v-if="editedSchedule.id" class="content-form">
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
              :schedule-id="Number(selectedId)"
              :period="editedSchedule.period"
              @submit="submitContentForm"
              @delete-content="deleteContentBtn"
            />
          </div>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <OpenModal @close="scheduleModal = false" v-if="scheduleModal">
    <ScheduleForm :is-add-form="true" @submit="submitScheduleForm">
      <template v-slot:title>
        {{ $t("schedule.form.main.add") }}
      </template>
    </ScheduleForm>
  </OpenModal>
  <OpenModal @close="contentModal = false" v-if="contentModal">
    <AddContentForm
      :schedule-id="Number(selectedId)"
      :period="editedSchedule.period"
      :default-date="defaultContentDate"
      @submit="submitContentForm"
    />
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
