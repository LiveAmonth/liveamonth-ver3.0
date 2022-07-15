<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import type { FormRules, FormInstance } from "element-plus/es";
import type { LoginType } from "@/modules/types/form/FormType";

const { submitForm, validateRequire } = useFormValidate();

const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  loginId: [validateRequire("member.loginId")],
  password: [validateRequire("member.password")],
});

const loginForm = reactive<LoginType>({
  loginId: "",
  password: "",
});
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
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.login") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
