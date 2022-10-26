<script lang="ts" setup>
import { reactive } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useMessageBox } from "@/composables/common/messageBox";
import { useRoute, useRouter } from "vue-router";
import type { LoginType } from "@/modules/types/member/MemberTypes";

const router = useRouter();
const route = useRoute();
const { returnUrl } = route.query;
const { error, isPending, login, isLoggedIn } = useAuth();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();

const form = reactive<LoginType>({
  loginId: "",
  password: "",
});

const submitForm = async () => {
  await login(form);
  if (isLoggedIn.value) {
    returnUrl
      ? await router.push({ path: String(returnUrl) })
      : await router.push({ name: "home" });
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
  <el-form ref="ruleFormRef" :model="form" label-position="top" status-icon>
    <el-form-item :label="labelMsg('member.loginId')" prop="loginId">
      <el-input v-model="form.loginId" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.password')" prop="password">
      <el-input v-model="form.password" show-password type="password" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#0f6778"
        size="large"
        style="width: 100%"
        @click="submitForm"
      >
        {{ buttonMsg("member.login") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
