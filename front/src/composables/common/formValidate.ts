import { computed, ref } from "vue";
import { useMemberStore } from "@/stores/member/member";
import { useMessageBox } from "@/composables/common/messageBox";
import { useDate } from "@/composables/common/date";
import type { FormItemRule } from "element-plus/es";
import type {
  ChangePasswordFormType,
  MemberCreateFormType,
} from "@/modules/types/member/MemberTypes";
import type {
  DatePeriodType,
  DateTimePeriodType,
} from "@/modules/types/schedule/ScheduleTypes";

export const useFormValidate = () => {
  const { getDate, isBetween, isSameDate, isBefore } = useDate();
  const { labelMsg, validationMsg } = useMessageBox();
  const store = useMemberStore();
  const isPending = ref(false);
  const isAvailable = computed(() => store.isAvailable);
  const validateRequire = (field: string): FormItemRule => {
    return {
      required: true,
      message: validationMsg("require.text", { field: labelMsg(field) }),
      trigger: "blur",
    };
  };

  const validateSelection = (field: string): FormItemRule => {
    return {
      required: true,
      message: validationMsg("require.select", { field: labelMsg(field) }),
      trigger: "change",
    };
  };

  const validatePattern = (
    pattern: RegExp | string,
    message: string
  ): FormItemRule => {
    return {
      pattern: pattern,
      message: validationMsg(message),
      trigger: "blur",
    };
  };

  const validateNumber = (): FormItemRule => {
    return {
      type: "number",
      message: validationMsg("number"),
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
      message: validationMsg("range", {
        field: labelMsg(field),
        min: min,
        max: max,
      }),
      trigger: "blur",
    };
  };

  const validateCost = (cost: number, min: number): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        if (min >= cost) {
          callback(
            new Error(
              validationMsg("min", {
                field: labelMsg("schedule.content.cost"),
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

  const validatePassword = (
    form: MemberCreateFormType | ChangePasswordFormType
  ): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        form.password == form.passwordCheck
          ? callback()
          : callback(new Error(validationMsg("password.recheck")));
      },
      trigger: "blur",
    };
  };

  const validateBirth = (form: MemberCreateFormType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        isBefore(form.birth, getDate(new Date()))
          ? callback()
          : callback(new Error(validationMsg("birth")));
      },
      trigger: "select",
    };
  };

  const validateDatePeriod = (period: DatePeriodType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        isBefore(period.startDate, period.endDate)
          ? callback()
          : callback(new Error(validationMsg("period.date")));
      },
      trigger: "select",
    };
  };

  const validateDateTimePeriod = (period: DateTimePeriodType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        isBefore(period.startDateTime, period.endDateTime)
          ? callback()
          : callback(new Error(validationMsg("period.time")));
      },
      trigger: "select",
    };
  };

  const validateSameDate = (period: DateTimePeriodType): FormItemRule => {
    return {
      validator: (rule, value, callback) => {
        isSameDate(period.startDateTime, period.endDateTime)
          ? callback()
          : callback(new Error(validationMsg("period.sameDate")));
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
          callback(new Error(validationMsg("period.range")));
        }
      },
      trigger: "select",
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
    isAvailable,
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
    validateSameDate,
    duplicateCheck,
  };
};
