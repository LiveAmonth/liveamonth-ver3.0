<script lang="ts" setup>
import { reactive } from "vue";
import { useAuth } from "@/composables/auth";
import { useMessageBox } from "@/composables/messageBox";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import type { LoginType } from "@/modules/types/form/FormType";

const router = useRouter();
const { error, isPending, login, isLoggedIn } = useAuth();
const { openMessageBox } = useMessageBox();
const { t } = useI18n();

const loginForm = reactive<LoginType>({
  loginId: "",
  password: "",
});

const submitForm = async () => {
  await login(loginForm);
  if (isLoggedIn.value) {
    await router.push({ name: "home" });
  }
  if (error.value) {
    await openMessageBox(t("form.message.wrongIdPw"));
    for (const key in loginForm) {
      loginForm[key] = "";
    }
  }
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="loginForm"
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
        @click="submitForm"
        >{{ $t("member.login") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
