import PageableRequest from "@/modules/class/PageableRequest";

export class SchedulePageableRequest extends PageableRequest {
  constructor() {
    super(1, 3, "id,desc");
  }
  getType(): string {
    return "schedule";
  }
}

export default SchedulePageableRequest;
