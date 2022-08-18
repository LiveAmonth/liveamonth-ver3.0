<script lang="ts" setup>
import { computed, onMounted, reactive, ref, watch } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import type {
  SignUpType,
  SignUpCheckType,
  ScheduleContentFormType,
} from "@/modules/types/form/FormType";
import { useFormValidate } from "@/composables/formValidate";
import { useMember } from "@/composables/member";
import { useMemberStore } from "@/stores/member";
import { useI18n } from "vue-i18n";
import { useMessageBox } from "@/composables/messageBox";
import { useRouter } from "vue-router";
import { ScheduleContentDetail } from "@/modules/class/ScheduleContentDetail";
import { useCalendarEvent } from "@/composables/calendarEvent";

const store = useMemberStore();
const router = useRouter();
const { t } = useI18n();
const { openMessageBox, openConfirmMessageBox } = useMessageBox();
const { validateRequire } = useFormValidate();
const { scheduleContentDetail, resetContent } = useCalendarEvent();
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
      openMessageBox("정보를 제대로 입력해주세요.");
    }
  });
};

const resetField = async () => {
  await resetContent();
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
    <span size="small" class="resetBtn" @click="resetField"
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
        class
        mode="time"
        v-model="scheduleContentDetail.start"
        timezone="Asia/Seoul"
      />
    </el-form-item>
    <el-form-item :label="$t('schedule.form.content.period.end')" prop="end">
      <v-date-picker
        readonly
        mode="time"
        v-model="scheduleContentDetail.end"
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
<style scoped lang="scss">
.resetBtn {
  float: right;

  &:hover {
    font-size: 14px;
    cursor: pointer;
  }
}
</style>
