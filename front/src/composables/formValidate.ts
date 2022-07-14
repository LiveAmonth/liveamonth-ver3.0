import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus/es";

export const useFormValidate = (rules: FormRules) => {
  const ruleFormRef = ref<FormInstance>();
  const formRules = reactive<FormRules>({
    rules,
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

  return {
    ruleFormRef,
      formRules,
    submitForm,
  };
};
