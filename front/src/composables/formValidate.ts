import { useI18n } from "vue-i18n";
import dayjs from "dayjs";
import type { FormItemRule } from "element-plus/es";
import type {
  ScheduleContentFormType,
  SignUpType,
} from "@/modules/types/form/FormType";
import { ref } from "vue";
import { useMemberStore } from "@/stores/member";
import type {
  DatePeriodType,
  DateTimePeriodType,
} from "@/modules/types/schedule/ScheduleType";
import { useDate } from "@/composables/date";

export const useFormValidate = () => {
  const { t } = useI18n();
  const { isBetween, isAfter } = useDate();
  const store = useMemberStore();
  const isPending = ref(false);

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

  const validateNumber = (): FormItemRule => {
    return {
      type: "number",
      message: t("validation.number"),
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

  const validateCost = (
    form: ScheduleContentFormType,
    min: number
  ): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (min >= form.cost) {
          callback(
            new Error(
              t("validation.min", {
                field: t("schedule.form.content.cost"),
                min: min,
              })
            )
          );
        } else {
          callback();
        }
      },
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
        if (isAfter(form.birth, new Date())) {
          callback(new Error(t("validation.birth")));
        } else {
          callback();
        }
      },
      trigger: "select",
    };
  };

  const validateDatePeriod = (period: DatePeriodType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (isAfter(period.startDate, period.endDate)) {
          callback(new Error(t("validation.period.date")));
        } else {
          callback();
        }
      },
      trigger: "select",
    };
  };

  const validateDateTimePeriod = (period: DateTimePeriodType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (isAfter(period.startDateTime, period.endDateTime)) {
          callback(new Error(t("validation.period.time")));
        } else {
          callback();
        }
      },
      trigger: "select",
    };
  };

  const validatePeriodRange = (
    period: DateTimePeriodType,
    range: DatePeriodType
  ): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (
          isBetween(period.startDateTime, range) &&
          isBetween(period.endDateTime, range)
        ) {
          callback();
        } else {
          callback(new Error(t("validation.period.range")));
        }
      },
      trigger: "select",
    };
  };

  const checkedField = (form: SignUpType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (form.duplicationCheck) {
          callback(new Error(t("validation.duplication.check")));
        } else {
          callback();
        }
      },
      trigger: "blur",
    };
  };

  const duplicateCheck = async (field: string, param: string) => {
    isPending.value = true;
    try {
      await store.duplicateCheck(field, param);
      isPending.value = false;
    } catch (error) {
      isPending.value = false;
    }
  };

  return {
    isPending,
    validateRequire,
    validateSelection,
    validatePattern,
    validateNumber,
    validateCost,
    validateRange,
    validatePassword,
    validateBirth,
    validateDatePeriod,
    validateDateTimePeriod,
    validatePeriodRange,
    checkedField,
    duplicateCheck,
  };
};
