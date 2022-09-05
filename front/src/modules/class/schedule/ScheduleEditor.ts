import type { ScheduleFormType } from "@/modules/types/form/FormType";
import type {
  DatePeriodType,
  ScheduleCardType,
} from "@/modules/types/schedule/ScheduleType";
import { useDate } from "@/composables/date";

const { convertDateToString } = useDate();
export default class ScheduleEditor implements ScheduleFormType {
  city: string;
  period: DatePeriodType;
  publicFlag: boolean;
  title: string;

  constructor(scheduleCard: ScheduleCardType) {
    this.title = scheduleCard.title;
    this.city = scheduleCard.city.code;
    this.period = scheduleCard.period;
    this.publicFlag = scheduleCard.publicFlag;
  }

  setForm(data: ScheduleCardType) {
    this.title = data.title;
    this.publicFlag = data.publicFlag;
    this.city = data.city.code;
    this.period.startDate = data.period.startDate;
    this.period.endDate = data.period.endDate;
  }
}
