import type { ScheduleFormType } from "@/modules/types/form/FormType";
import type {
  DatePeriodType,
  ScheduleCardType,
} from "@/modules/types/schedule/ScheduleType";

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

  getObject() {
    return {
      title: this.title,
      city: this.city,
      publicFlag: this.publicFlag,
      startDate: this.period.startDate,
      endDate: this.period.endDate,
    };
  }
}
