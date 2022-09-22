<script lang="ts" setup>
import { reactive } from "vue";
import { useAuth } from "@/composables/auth";
import { useMessageBox } from "@/composables/messageBox";
import { useRouter } from "vue-router";
import type { LoginType } from "@/modules/types/form/FormType";

const router = useRouter();
const { error, isPending, login, isLoggedIn } = useAuth();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();

const form = reactive<LoginType>({
  loginId: "",
  password: "",
});

const submitForm = async () => {
  await login(form);
  if (isLoggedIn.value) {
    await router.push({ name: "home" });
  }
  if (error.value) {
    await openMessageBox(resultMsg("wrongIdPw"));
    for (const key in form) {
      form[key] = "";
    }
  }
};
</script>

<template>
  <el-form ref="ruleFormRef" :model="form" status-icon label-position="top">
    <el-form-item :label="labelMsg('member.loginId')" prop="loginId">
      <el-input v-model="form.loginId" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.password')" prop="password">
      <el-input v-model="form.password" type="password" show-password />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm"
        >{{ buttonMsg("member.login") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
