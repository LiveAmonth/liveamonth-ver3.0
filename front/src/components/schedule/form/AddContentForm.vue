<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";
import { reactive, ref, watch } from "vue";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import type { PropType } from "vue";
import type { FormInstance } from "element-plus/es";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

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
const { openMessage, openMessageBox } = useMessageBox();
const { t } = useI18n();

const contentForm = reactive<ScheduleContentEditor>(
  new ScheduleContentEditor(props.period)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await addContent(props.scheduleId, contentForm).then(() => {
        openMessage(t("form.message.content.add"));
        emits("submit");
      });
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};

watch(
  () => props.defaultDate,
  () => {
    contentForm.clear();
  }
);
</script>
<template>
  <el-card>
    <el-form
      ref="ruleFormRef"
      :model="contentForm"
      :rules="contentForm.getRules()"
      label-width="75px"
      status-icon
    >
      <div class="d-flex justify-content-between align-items-center mb-2">
        <SmallTitleSlot>
          {{ $t("schedule.form.content.add") }}
        </SmallTitleSlot>
      </div>
      <el-form-item :label="$t('common.title')" prop="title">
        <el-input
          v-model="contentForm.title"
          :placeholder="
            $t('common.please-input', {
              field: $t('common.title'),
            })
          "
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item :label="$t('schedule.form.content.content')" prop="content">
        <el-input
          v-model="contentForm.content"
          :placeholder="
            $t('common.please-input', {
              field: $t('schedule.form.content.content'),
            })
          "
          :rows="2"
          type="textarea"
        />
      </el-form-item>
      <el-form-item :label="$t('schedule.form.content.cost')" prop="cost">
        <el-input
          v-model="contentForm.cost"
          :placeholder="
            $t('common.please-input', {
              field: $t('schedule.form.content.cost'),
            })
          "
          type="number"
          style="width: 200px"
        >
          <template #append>
            {{ $t("schedule.form.content.won") }}
          </template>
        </el-input>
      </el-form-item>
      <el-form-item
        :label="$t('schedule.form.content.period.start')"
        prop="period"
        class="period-item"
      >
        <el-date-picker
          v-model="contentForm.timePeriod.startDateTime"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="datetime"
          format="MM-DD HH:mm"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item
        :label="$t('schedule.form.content.period.end')"
        prop="period"
        class="period-item"
      >
        <el-date-picker
          v-model="contentForm.timePeriod.endDateTime"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="datetime"
          format="MM-DD HH:mm"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
    </el-form>
    <div class="d-flex justify-content-end">
      <el-button @click="submitForm(ruleFormRef)">
        {{ $t("common.button.add") }}
      </el-button>
      <el-button @click="contentForm.clear(period.startDate)">
        {{ $t("common.button.clear") }}
      </el-button>
    </div>
  </el-card>
</template>
<style lang="scss" scoped></style>