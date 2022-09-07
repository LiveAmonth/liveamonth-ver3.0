<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";
import { computed, onMounted, reactive, ref, watch } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import type { PropType } from "vue";
import type { FormInstance, FormRules } from "element-plus/es";
import type { ScheduleContentType } from "@/modules/types/schedule/ScheduleType";
import { useCalendarEvent } from "@/composables/calendarEvent";

const props = defineProps({
  isAddition: {
    type: Boolean,
    required: false,
    default: false,
  },
  content: {
    type: Object as PropType<ScheduleContentType> | null,
    required: false,
    default: null,
  },
});
const emits = defineEmits(["submit", "deleteContent"]);
const { validateRequire, validateDateTimePeriod, validateMin, validateNumber } =
  useFormValidate();
const { openMessage, openMessageBox } = useMessageBox();
const { t } = useI18n();

const isEdit = ref<boolean>(false);
const contentForm = reactive<ScheduleContentEditor>(
  new ScheduleContentEditor()
);
const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  title: [validateRequire("common.title")],
  content: [validateRequire("schedule.form.content.content")],
  cost: [validateMin("schedule.form.content.cost", 0), validateNumber()],
  period: [validateDateTimePeriod(contentForm.timePeriod)],
});

watch(
  () => props.content,
  () => {
    contentForm.setForm(props.content);
  }
);

const cancelEdit = () => {
  contentForm.setForm(props.content);
  isEdit.value = false;
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      if (props.isAddition) {
        // await editContent(props.schedule.id, scheduleForm).then(() => {
        //   isEdit.value = false;
        //   openMessage(t("form.message.schedule.update"));
        //   emits("submit", true);
        // });
      } else {
        // await addSchedule(simpleProfile.value.id, scheduleForm).then(() => {
        //   openMessage(t("form.message.schedule.add"));
        //   emits("submit");
        // });
      }
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>
<template>
  <el-card>
    <el-form
      ref="ruleFormRef"
      :disabled="!isEdit"
      :model="contentForm"
      :rules="rules"
      status-icon
    >
      <div class="d-flex justify-content-between align-items-center mb-2">
        <SmallTitleSlot>
          <slot name="title"></slot>
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
          style="width: 200px"
        >
          <template #append>원</template>
        </el-input>
      </el-form-item>
      <el-form-item
        :label="$t('schedule.form.content.period.start')"
        prop="period"
        class="period-item"
      >
        <el-time-picker
          v-model="contentForm.timePeriod.startDateTime"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item
        :label="$t('schedule.form.content.period.end')"
        prop="period"
        class="period-item"
      >
        <el-time-picker
          v-model="contentForm.timePeriod.endDateTime"
          :placeholder="$t('common.pick-day')"
          style="width: 200px"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
    </el-form>
    <div v-if="!isAddition" class="d-flex justify-content-end">
      <template v-if="contentForm.title">
        <el-button v-if="!isEdit" @click="isEdit = true"> 수정</el-button>
        <el-button v-if="!isEdit" @click="emits('deleteContent')">
          삭제
        </el-button>
        <template v-else>
          <el-button @click="submitForm(ruleFormRef)"> 업데이트</el-button>
          <el-button @click="cancelEdit"> 취소</el-button>
        </template>
      </template>
    </div>
    <div v-else class="d-flex justify-content-end">
      <el-button @click="submitForm(ruleFormRef)"> 추가</el-button>
      <el-button @click="contentForm.clear()"> 초기화</el-button>
    </div>
  </el-card>
</template>
<style lang="scss" scoped></style>
