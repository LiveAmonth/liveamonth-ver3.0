import { reactive } from "vue";
import { useDate } from "@/composables/date";
import { useFormValidate } from "@/composables/formValidate";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import type {
  DateTimePeriodType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import type { EventApi } from "@fullcalendar/common";
import type { FormRules } from "element-plus/es";
import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

const { getDateTime } = useDate();

export default class ScheduleContentEditor implements ScheduleContentFormType {
  content: string;
  cost: number;
  title: string;
  timePeriod: DateTimePeriodType;
  schedulePeriod: DatePeriodType;

  constructor(period: DatePeriodType) {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod = {
      startDateTime: getDateTime(period.startDate),
      endDateTime: getDateTime(period.startDate),
    };
    this.schedulePeriod = period;
  }

  setAttr(event: EventApi) {
    this.title = event.title;
    this.content = event.extendedProps.content;
    this.cost = event.extendedProps.cost;
    this.timePeriod.startDateTime = getDateTime(event.start as Date);
    this.timePeriod.endDateTime = getDateTime(event.end as Date);
  }

  setDefaultDate(date: string): void {
    this.timePeriod.startDateTime = getDateTime(date);
    this.timePeriod.endDateTime = getDateTime(date);
  }

  setForm(data: ScheduleContentType) {
    this.title = data.title;
    this.content = data.content;
    this.cost = data.cost;
    this.timePeriod.startDateTime = data.timePeriod.startDateTime;
    this.timePeriod.endDateTime = data.timePeriod.endDateTime;
  }

  clear(): void {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod.startDateTime = getDateTime(this.schedulePeriod.startDate);
    this.timePeriod.endDateTime = getDateTime(this.schedulePeriod.startDate);
  }

  getRules(): FormRules {
    const {
      validateRequire,
      validateDateTimePeriod,
      validateCost,
      validatePeriodRange,
      validateSameDate,
    } = useFormValidate();

    return reactive<FormRules>({
      title: [validateRequire("common.title")],
      content: [validateRequire("schedule.form.content.content")],
      cost: [validateCost(this, 0)],
      period: [
        validateSameDate(this.timePeriod),
        validatePeriodRange(this.timePeriod, this.schedulePeriod),
        validateDateTimePeriod(this.timePeriod),
      ],
    });
  }
}
