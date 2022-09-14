import { reactive } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import type { ScheduleFormType } from "@/modules/types/form/FormType";
import type {
  DatePeriodType,
  ScheduleCardType,
} from "@/modules/types/schedule/ScheduleType";
import type { FormRules } from "element-plus/es";

export default class ScheduleEditor implements ScheduleFormType {
  title: string;
  city: string;
  period: DatePeriodType;
  publicFlag: boolean;

  constructor() {
    this.title = "";
    this.publicFlag = false;
    this.city = "";
    this.period = { startDate: "", endDate: "" };
  }

  setForm(data: ScheduleCardType) {
    this.title = data.title;
    this.publicFlag = data.publicFlag;
    this.city = data.city.code;
    this.period.startDate = data.period.startDate;
    this.period.endDate = data.period.endDate;
  }

  clear(): void {
    this.title = "";
    this.publicFlag = false;
    this.city = "";
    this.period.startDate = "";
    this.period.endDate = "";
  }

  getRules(): FormRules {
    const { validateRequire, validateSelection, validateDatePeriod } =
      useFormValidate();

    return reactive<FormRules>({
      title: [validateRequire("common.title")],
      city: [validateSelection("city.title")],
      period: [validateDatePeriod(this.period)],
    });
  }
}
