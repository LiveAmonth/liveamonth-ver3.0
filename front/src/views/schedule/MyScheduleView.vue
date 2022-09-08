<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import ScheduleCalendar from "@/components/schedule/calendar/ScheduleCalendar.vue";
import ScheduleForm from "@/components/schedule/form/ScheduleForm.vue";
import OpenModal from "@/components/common/OpenModal.vue";
import { Plus } from "@element-plus/icons-vue";
import { onMounted, ref } from "vue";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";
import { useCalendarEvent } from "@/composables/calendarEvent";
import ScheduleContentForm from "@/components/schedule/form/ScheduleContentForm.vue";

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
const { selectedContent, resetContent } = useCalendarEvent();
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
      resetContent();
    });
  }
};

const submitScheduleForm = async (isEdit = false) => {
  await getMySchedules(props.loginId).then(() => {
    if (!isEdit) {
      selectedId.value = mySchedules.value[0].id;
      changeSchedule();
      modal.value = false;
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

const submitContentForm = async (isEdit = false) => {
  console.log(isEdit);
};

const deleteContentBtn = async () => {
  console.log("삭제");
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
            :editable="false"
            :init-date="initDate"
            :manage-state="true"
          />
        </el-col>
        <el-col :span="6" class="mt-4">
          <div class="d-flex justify-content-end mt-3">
            <el-button
              class="d-flex justify-content-between"
              @click="modal = true"
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
          <div class="d-flex justify-content-end mt-3">
            <el-button
              class="d-flex justify-content-between"
              @click="modal = true"
              size="large"
              text
            >
              <el-icon class="me-1">
                <Plus />
              </el-icon>
              {{ $t("schedule.form.content.add") }}
            </el-button>
          </div>
          <ScheduleContentForm
            :content="selectedContent"
            @submit="submitContentForm"
            @delete-content="deleteContentBtn"
          >
            <template v-slot:title>
              {{ $t("schedule.title.content") }}
            </template>
          </ScheduleContentForm>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
  <OpenModal @close="modal = false" v-if="modal">
    <ScheduleForm @submit="submitScheduleForm">
      <template v-slot:title>
        {{ $t("schedule.form.main.add") }}
      </template>
    </ScheduleForm>
  </OpenModal>
  <OpenModal @close="modal = false" v-if="modal">
    <ScheduleForm @submit="submitScheduleForm">
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
