<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";
import { reactive, ref, watch } from "vue";
import { useSchedule } from "@/composables/schedule";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import type { PropType } from "vue";
import type { FormInstance } from "element-plus/es";
import type { ScheduleContentType } from "@/modules/types/schedule/ScheduleType";
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
  content: {
    type: Object as PropType<ScheduleContentType> | null,
    required: false,
    default: null,
  },
});
const emits = defineEmits(["submit", "deleteContent"]);
const { editContent } = useSchedule();
const { openMessage, openMessageBox } = useMessageBox();
const { t } = useI18n();

const isEdit = ref<boolean>(!props.content);
const contentForm = reactive<ScheduleContentEditor>(
  new ScheduleContentEditor(props.period)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await editContent(props.content.id, contentForm).then(() => {
        isEdit.value = false;
        openMessage(t("form.message.content.update"));
        emits("submit", true);
      });
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};

const cancelEdit = () => {
  contentForm.setForm(props.content);
  isEdit.value = false;
};

watch(
  () => props.content,
  () => {
    contentForm.setForm(props.content);
  }
);
</script>
<template>
  <el-card>
    <div class="d-flex justify-content-between align-items-center mb-2">
      <SmallTitleSlot>
        {{ $t("schedule.title.content") }}
      </SmallTitleSlot>
    </div>
    <template v-if="contentForm.title">
      <el-form
        ref="ruleFormRef"
        :disabled="!isEdit"
        :model="contentForm"
        :rules="contentForm.getRules()"
        label-width="75px"
        status-icon
      >
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
        <el-form-item
          :label="$t('schedule.form.content.content')"
          prop="content"
        >
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
            <template #append> 원</template>
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
        <template v-if="content">
          <template v-if="!isEdit">
            <el-button @click="isEdit = true"> 수정</el-button>
            <el-button @click="emits('deleteContent', content.id)">
              삭제</el-button
            >
          </template>
          <template v-else>
            <el-button @click="submitForm(ruleFormRef)"> 업데이트</el-button>
            <el-button @click="cancelEdit"> 취소</el-button>
          </template>
        </template>
      </div>
    </template>
    <template v-else> 스케줄표에서 컨텐츠를 선택해주세요. </template>
  </el-card>
</template>
<style lang="scss" scoped></style>
