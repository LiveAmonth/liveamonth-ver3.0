import { useDate } from "@/composables/date";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import type {
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

  constructor(date: string) {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod = {
      startDateTime: date,
      endDateTime: date,
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
    this.timePeriod.startDateTime = data.timePeriod.startDateTime;
    this.timePeriod.endDateTime = data.timePeriod.endDateTime;
  }

  clear(date: string): void {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod.startDateTime = date;
    this.timePeriod.endDateTime = date;
  }
}

export default ScheduleContentEditor;
