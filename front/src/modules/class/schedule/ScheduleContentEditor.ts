import { useDate } from "@/composables/date";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import type {
  DateTimePeriodType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import type { EventApi } from "@fullcalendar/common";

const { convertDateTime } = useDate();

export class ScheduleContentEditor implements ScheduleContentFormType {
  content: string;
  cost: number;
  title: string;
  timePeriod: DateTimePeriodType;

  constructor() {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod = { startDateTime: "", endDateTime: "" };
  }

  setAttr(event: EventApi) {
    this.title = event.title;
    this.content = event.extendedProps.content;
    this.cost = event.extendedProps.cost;
    this.timePeriod.startDateTime = convertDateTime(event.start);
    this.timePeriod.endDateTime = convertDateTime(event.end);
  }

  setForm(data: ScheduleContentType) {
    this.title = data.title;
    this.content = data.content;
    this.cost = data.cost;
    this.timePeriod = data.timePeriod;
  }

  clear(): void {
    this.title = "";
    this.content = "";
    this.cost = 0;
    this.timePeriod.startDateTime = "";
    this.timePeriod.endDateTime = "";
  }
}

export default ScheduleContentEditor;
