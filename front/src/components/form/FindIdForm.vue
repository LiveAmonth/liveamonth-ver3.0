<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import type { FormInstance, FormRules } from "element-plus/es";
import type { FindIdType } from "@/modules/types/form/FormType";

const { validateRequire } = useFormValidate();

const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  email: [validateRequire("member.email")],
});

const findIdForm = reactive<FindIdType>({
  email: "",
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findIdForm"
    :rules="rules"
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
