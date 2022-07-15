import { useI18n } from "vue-i18n";
import dayjs from "dayjs";
import type { FormInstance, FormItemRule } from "element-plus/es";
import type { SignUpType } from "@/modules/types/form/FormType";

export const useFormValidate = () => {
  const { t } = useI18n();
  const validateRequire = (field: string): FormItemRule => {
    return {
      required: true,
      message: t("validation.require.text", { field: t(field) }),
      trigger: "blur",
    };
  };
  const validateSelection = (field: string): FormItemRule => {
    return {
      required: true,
      message: t("validation.require.select", { field: t(field) }),
      trigger: "change",
    };
  };
  const validatePattern = (
    pattern: RegExp | string,
    message: string
  ): FormItemRule => {
    return {
      pattern: pattern,
      message: t(message),
      trigger: "blur",
    };
  };
  const validateRange = (
    field: string,
    min: number,
    max: number
  ): FormItemRule => {
    return {
      min: min,
      max: max,
      message: t("validation.range", { field: t(field), min: min, max: max }),
      trigger: "blur",
    };
  };
  const validatePassword = (form: SignUpType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (value !== form.password) {
          callback(new Error(t("validation.password.recheck")));
        } else {
          callback();
        }
      },
      trigger: "blur",
    };
  };
  const validateBirth = (form: SignUpType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (dayjs(form.birth).isAfter(dayjs())) {
          callback(new Error(t("validation.birth")));
        } else {
          callback();
        }
      },
      trigger: "select",
    };
  };
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
    validateRequire,
    validateSelection,
    validatePattern,
    validateRange,
    validatePassword,
    validateBirth,
    submitForm,
  };
};
