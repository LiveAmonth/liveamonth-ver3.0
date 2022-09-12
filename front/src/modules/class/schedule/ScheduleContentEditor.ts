import { useDate } from "@/composables/date";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import type {
  DatePeriodType,
  DateTimePeriodType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import type { EventApi } from "@fullcalendar/common";

const { getDateTime } = useDate();

export class ScheduleContentEditor implements ScheduleContentFormType {
  content: string;
  cost: number;
  title: string;
  timePeriod: DateTimePeriodType;

  constructor(period: DatePeriodType) {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod = {
      startDateTime: getDateTime(period.startDate),
      endDateTime: getDateTime(period.startDate),
    };
  }

  setAttr(event: EventApi) {
    this.title = event.title;
    this.content = event.extendedProps.content;
    this.cost = event.extendedProps.cost;
    this.timePeriod.startDateTime = getDateTime(event.start as Date);
    this.timePeriod.endDateTime = getDateTime(event.end as Date);
  }

  setForm(data: ScheduleContentType) {
    this.title = data.title;
    this.content = data.content;
    this.cost = data.cost;
    this.timePeriod = data.timePeriod;
  }

  clear(period: DatePeriodType): void {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod.startDateTime = getDateTime(period.startDate);
    this.timePeriod.endDateTime = getDateTime(period.startDate);
  }
}

export default ScheduleContentEditor;
