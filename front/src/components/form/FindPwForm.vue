<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import type { FormInstance, FormRules } from "element-plus/es";
import type { FindPwType } from "@/modules/types/form/FormType";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useI18n } from "vue-i18n";
import { useMessageBox } from "@/composables/messageBox";
import { useMember } from "@/composables/member";

const store = useMemberStore();
const { t } = useI18n();
const { openMessageBox } = useMessageBox();
const { error, isPending, findPw } = useMember();
const { validateRequire } = useFormValidate();

const emit = defineEmits(["find-pw"]);
const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  loginId: [validateRequire("member.loginId")],
  email: [validateRequire("member.email")],
});

const findPwForm = reactive<FindPwType>({
  loginId: "",
  email: "",
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await findPw(findPwForm);
      if (error.value) {
        await openMessageBox(t("form.message.noMember"));
        for (const key in findPwForm) {
          findPwForm[key] = "";
        }
      } else if (store.foundId) {
        emit("find-pw");
      }
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findPwForm"
    :rules="rules"
    label-position="top"
    status-icon
  >
    <el-form-item :label="$t('member.loginId')" prop="loginId">
      <el-input v-model="findPwForm.loginId" />
    </el-form-item>
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="findPwForm.email" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.findPw") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
