<script lang="ts" setup>
import { reactive } from "vue";
import { useFormValidate } from "@/composables/formValidate";

const rules = {
  email: [{ required: true, message: "Please input email", trigger: "blur" }],
};

const { ruleFormRef, formRules, submitForm } = useFormValidate(rules);

const findIdForm = reactive({
  email: "",
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findIdForm"
    :rules="formRules"
    status-icon
    label-position="top"
  >
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="findIdForm.email" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="false"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.findId") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
