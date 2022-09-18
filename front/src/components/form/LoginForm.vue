<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import { useAuth } from "@/composables/auth";
import { useMessageBox } from "@/composables/messageBox";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import { useMember } from "@/composables/member";
import type { FormRules, FormInstance } from "element-plus/es";
import type { LoginType } from "@/modules/types/form/FormType";

const router = useRouter();
const { t } = useI18n();
const { error, isPending, login, isLoggedIn } = useAuth();
const { getSimpleProfile } = useMember();
const { openMessageBox } = useMessageBox();
const { validateRequire } = useFormValidate();

const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  loginId: [validateRequire("member.loginId")],
  password: [validateRequire("member.password")],
});

const loginForm = reactive<LoginType>({
  loginId: "",
  password: "",
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await login(loginForm);
      if (error.value) {
        await openMessageBox(t("form.message.wrongIdPw"));
        for (const key in loginForm) {
          loginForm[key] = "";
        }
      } else if (isLoggedIn.value) {
        await getSimpleProfile();
        await router.push({ name: "home" });
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
    :model="loginForm"
    :rules="rules"
    status-icon
    label-position="top"
  >
    <el-form-item :label="$t('member.loginId')" prop="loginId">
      <el-input v-model="loginForm.loginId" />
    </el-form-item>
    <el-form-item :label="$t('member.password')" prop="password">
      <el-input v-model="loginForm.password" type="password" show-password />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.login") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
