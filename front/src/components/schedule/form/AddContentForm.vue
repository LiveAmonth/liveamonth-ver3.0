<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { reactive, ref, watch } from "vue";
import { useSchedule } from "@/composables/schedule/schedule";
import { useMessageBox } from "@/composables/common/messageBox";
import { ScheduleContentEditor } from "@/modules/types/schedule/ScheduleTypes";
import type { PropType } from "vue";
import type { FormInstance } from "element-plus/es";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleTypes";

const props = defineProps({
  scheduleId: {
    type: Number,
    required: true,
  },
  period: {
    type: Object as PropType<DatePeriodType>,
    required: true,
  },
  defaultDate: {
    type: String,
    required: true,
  },
});
const emits = defineEmits(["submit"]);
const { addContent } = useSchedule();
const {
  buttonMsg,
  titleMsg,
  labelMsg,
  openMessage,
  openMessageBox,
  inputPhMsg,
  resultMsg,
  placeholderMsg,
} = useMessageBox();

const form = reactive<ScheduleContentEditor>(
  new ScheduleContentEditor(props.period)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await addContent(props.scheduleId, form).then(() => {
        openMessage(resultMsg("content.add"));
        emits("submit");
      });
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};

watch(
  () => props.defaultDate,
  () => {
    form.clear();
  }
);
</script>
<template>
  <el-card>
    <el-form
      ref="ruleFormRef"
      :model="form"
      :rules="form.getRules()"
      label-width="75px"
      status-icon
    >
      <div class="d-flex justify-content-between align-items-center mb-2">
        <SmallTitleSlot :title="titleMsg('schedule.content.new')" />
      </div>
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
          style="width: 200px"
          type="number"
        >
          <template #append>
            {{ labelMsg("schedule.won") }}
          </template>
        </el-input>
      </el-form-item>
      <el-form-item
        :label="labelMsg('schedule.content.start')"
        class="period-item"
        prop="period"
      >
        <el-date-picker
          v-model="form.timePeriod.startDateTime"
          :placeholder="placeholderMsg('pick-day')"
          format="MM-DD HH:mm"
          style="width: 200px"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item
        :label="labelMsg('schedule.content.end')"
        class="period-item"
        prop="period"
      >
        <el-date-picker
          v-model="form.timePeriod.endDateTime"
          :placeholder="placeholderMsg('pick-day')"
          format="MM-DD HH:mm"
          style="width: 200px"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
    </el-form>
    <div class="d-flex justify-content-end">
      <el-button @click="submitForm(ruleFormRef)">
        {{ buttonMsg("add") }}
      </el-button>
      <el-button @click="form.clear()">
        {{ buttonMsg("reset") }}
      </el-button>
    </div>
  </el-card>
</template>
<style lang="scss" scoped></style>
