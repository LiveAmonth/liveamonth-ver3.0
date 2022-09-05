import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import type {
  DateTimePeriodType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";

export class ScheduleContentEditor implements ScheduleContentFormType {
  content: string;
  cost: number;
  title: string;
  timePeriod: DateTimePeriodType;

  constructor(scheduleContent: ScheduleContentType) {
    this.title = scheduleContent.title;
    this.content = scheduleContent.content;
    this.cost = scheduleContent.cost;
    this.timePeriod = scheduleContent.timePeriod;
  }

  setAttr(event: any) {
    this.title = event.title;
    this.content = event.extendedProps.content;
    this.cost = event.extendedProps.cost;
    this.timePeriod.startDateTime = event.startStr;
    this.timePeriod.endDateTime = event.endStr;
  }
}

export default ScheduleContentEditor;
