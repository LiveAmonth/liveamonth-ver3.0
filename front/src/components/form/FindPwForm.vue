<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import type { FormInstance, FormRules } from "element-plus/es";
import type { FindPwType } from "@/modules/types/form/FormType";

const { submitForm, validateRequire } = useFormValidate();

const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  loginId: [validateRequire("member.loginId")],
  email: [validateRequire("member.email")],
});

const findPwForm = reactive<FindPwType>({
  loginId: "",
  email: "",
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findPwForm"
    :rules="rules"
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
