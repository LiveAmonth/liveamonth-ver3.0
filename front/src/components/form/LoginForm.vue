<script lang="ts" setup>
import { reactive } from "vue";
import { useFormValidate } from "@/composables/formValidate";

const rules = {
  loginId: [{ required: true, message: "Please input id", trigger: "blur" }],
  password: [
    { required: true, message: "Please input password", trigger: "blur" },
  ],
};

const { ruleFormRef, formRules, submitForm } = useFormValidate(rules);

const loginForm = reactive({
  loginId: "",
  password: "",
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="loginForm"
    :rules="formRules"
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
        >{{ $t("member.login") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss">
.el-button {
  width: 100%;
}
</style>
