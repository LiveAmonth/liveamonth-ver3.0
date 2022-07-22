<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import { useAuth } from "@/composables/auth";
import type { FormRules, FormInstance } from "element-plus/es";
import type { LoginType } from "@/modules/types/form/FormType";
import { useMessageBox } from "@/composables/messageBox";
import { useRouter } from "vue-router";

const router = useRouter();
const { openMessageBox } = useMessageBox();
const { error, isPending, login } = useAuth();
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
  await formEl.validate((valid) => {
    if (valid) {
      login(loginForm);
      if (!error) {
        router.push({ name: "home" });
      } else {
        openMessageBox("아이디 혹은 비밀번호가 틀렸습니다.");
        for (const key in loginForm) {
          loginForm[key] = "";
        }
      }
    } else {
      openMessageBox("정보를 제대로 입력해주세요");
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
