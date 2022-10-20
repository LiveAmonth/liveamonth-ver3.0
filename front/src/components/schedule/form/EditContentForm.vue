<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { reactive, ref, watch } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useCalendarEvent } from "@/composables/schedule/calendarEvent";
import { useMessageBox } from "@/composables/common/messageBox";
import { ScheduleContentEditor } from "@/modules/types/schedule/ScheduleTypes";
import type { FormInstance } from "element-plus/es";

defineProps({
  scheduleId: {
    type: Number,
    required: true,
  },
});
const emits = defineEmits(["submit", "deleteContent"]);
const { editedSchedule, editContent } = useSchedule();
const { selectedContent } = useCalendarEvent();
const {
  titleMsg,
  labelMsg,
  buttonMsg,
  inputPhMsg,
  resultMsg,
  placeholderMsg,
  openMessage,
  openMessageBox,
} = useMessageBox();

const isEdit = ref<boolean>(!selectedContent.value);
const form = reactive<ScheduleContentEditor>(
  new ScheduleContentEditor(editedSchedule.value.period)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await editContent(selectedContent.value.id, form).then(() => {
        isEdit.value = false;
        openMessage(resultMsg("content.update"));
        emits("submit", true);
      });
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};

const cancelEdit = () => {
  form.setForm(selectedContent.value);
  isEdit.value = false;
};

watch(
  () => editedSchedule.value,
  () => {
    form.schedulePeriod = editedSchedule.value.period;
  }
);

watch(
  () => selectedContent.value,
  () => {
    if (selectedContent.value.title) {
      form.setForm(selectedContent.value);
    }
  }
);
</script>
<template>
  <el-card>
    <div class="d-flex justify-content-between align-items-center mb-2">
      <SmallTitleSlot :title="titleMsg(`schedule.content.info`)" />
    </div>
    <template v-if="scheduleId && selectedContent.title">
      <el-form
        ref="ruleFormRef"
        :disabled="!isEdit"
        :model="form"
        :rules="form.getRules()"
        label-width="75px"
        status-icon
      >
        <el-form-item :label="labelMsg('title')" prop="title">
          <el-input
            v-model="form.title"
            :placeholder="inputPhMsg(labelMsg('title'))"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item :label="labelMsg('content')" prop="content">
          <el-input
            v-model="form.content"
            :placeholder="inputPhMsg(labelMsg('content'))"
            :rows="2"
            type="textarea"
          />
        </el-form-item>
        <el-form-item :label="labelMsg('schedule.content.cost')" prop="cost">
          <el-input
            v-model="form.cost"
            :placeholder="inputPhMsg(labelMsg('schedule.content.cost'))"
            type="number"
            style="width: 200px"
          >
            <template #append>
              {{ labelMsg("schedule.won") }}
            </template>
          </el-input>
        </el-form-item>
        <el-form-item
          :label="labelMsg('schedule.content.start')"
          prop="period"
          class="period-item"
        >
          <el-date-picker
            v-model="form.timePeriod.startDateTime"
            :placeholder="placeholderMsg('pick-day')"
            style="width: 200px"
            type="datetime"
            format="MM-DD HH:mm"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item
          :label="labelMsg('schedule.content.end')"
          prop="period"
          class="period-item"
        >
          <el-date-picker
            v-model="form.timePeriod.endDateTime"
            :placeholder="placeholderMsg('pick-day')"
            style="width: 200px"
            type="datetime"
            format="MM-DD HH:mm"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
      </el-form>
      <div class="d-flex justify-content-end">
        <template v-if="selectedContent">
          <template v-if="!isEdit">
            <el-button @click="isEdit = true">
              {{ buttonMsg("edit") }}
            </el-button>
            <el-button @click="emits('deleteContent', selectedContent.id)">
              {{ buttonMsg("delete") }}
            </el-button>
          </template>
          <template v-else>
            <el-button @click="submitForm(ruleFormRef)">
              {{ buttonMsg("update") }}
            </el-button>
            <el-button @click="cancelEdit">
              {{ buttonMsg("cancel") }}
            </el-button>
          </template>
        </template>
      </div>
    </template>
    <template v-else>
      <span class="empty-content">
        {{ resultMsg("schedule.empty.content") }}
      </span>
    </template>
  </el-card>
</template>
<style lang="scss" scoped>
.empty-content {
  font-size: 0.93rem;
}
</style>
