<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { useFormValidate } from "@/composables/formValidate";
import { useI18n } from "vue-i18n";
import { useMessageBox } from "@/composables/messageBox";
import { useCalendarEvent } from "@/composables/calendarEvent";

const { t } = useI18n();
const { openMessageBox } = useMessageBox();
const { validateRequire } = useFormValidate();
const { scheduleContentDetail } = useCalendarEvent();
const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  loginId: [validateRequire("member.loginId")],
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log("추가");
    } else {
      openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="scheduleContentDetail"
    :rules="rules"
    label-position="top"
    label-width="120px"
    status-icon
  >
    <span class="resetBtn" size="small" @click="resetField"
      ><el-icon><Refresh /></el-icon>{{ $t("form.reset") }}
    </span>
    <el-form-item :label="$t('schedule.form.content.title')" prop="title">
      <el-input v-model="scheduleContentDetail.title" />
    </el-form-item>
    <el-form-item :label="$t('schedule.form.content.content')" prop="content">
      <el-input
        v-model="scheduleContentDetail.content"
        autosize
        type="textarea"
      />
    </el-form-item>
    <el-form-item :label="$t('schedule.form.content.cost')" prop="cost">
      <el-input v-model="scheduleContentDetail.cost">
        <template #append> 원</template>
      </el-input>
    </el-form-item>
    <el-form-item
      :label="$t('schedule.form.content.period.start')"
      prop="start"
    >
      <v-date-picker
        v-model="scheduleContentDetail.start"
        class
        mode="time"
        timezone="Asia/Seoul"
      />
    </el-form-item>
    <el-form-item :label="$t('schedule.form.content.period.end')" prop="end">
      <v-date-picker
        v-model="scheduleContentDetail.end"
        mode="time"
        readonly
        timezone="Asia/Seoul"
      />
    </el-form-item>
    <el-form-item>
      <el-button
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("schedule.form.content.add") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
<style lang="scss" scoped>
.resetBtn {
  float: right;

  &:hover {
    font-size: 14px;
    cursor: pointer;
  }
}
</style>
