<script lang="ts" setup>
import { reactive } from "vue";
import { useFormValidate } from "@/composables/formValidate";

const rules = {
  loginId: [{ required: true, message: "Please input id", trigger: "blur" }],
  email: [{ required: true, message: "Please input email", trigger: "blur" }],
};

const { ruleFormRef, formRules, submitForm } = useFormValidate(rules);

const findPwForm = reactive({
  loginId: "",
  email: "",
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findPwForm"
    :rules="formRules"
    status-icon
    label-position="top"
  >
    <el-form-item :label="$t('member.loginId')" prop="loginId">
      <el-input v-model="findPwForm.loginId" />
    </el-form-item>
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="findPwForm.email" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="false"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.findPw") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
