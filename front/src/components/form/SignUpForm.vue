<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";

const ruleFormRef = ref<FormInstance>();
const loginForm = reactive({
  loginId: "",
  password: "",
});

const rules = reactive<FormRules>({
  loginId: [{ required: true, message: "Please input id", trigger: "blur" }],
  password: [
    { required: true, message: "Please input password", trigger: "blur" },
  ],
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log("submit!");
    } else {
      console.log("error submit!", fields);
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
        :loading="false"
        color="#004A55"
        size="large"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.login") }}</el-button
      >
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss">
.el-button {
  width: 100%;
}
</style>
