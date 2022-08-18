import type { ScheduleContentFormType } from "@/modules/types/form/FormType";

export class ScheduleContentDetail implements ScheduleContentFormType {
  private _content: string;
  private _cost: number;
  private _title: string;
  private _end: string | Date;
  private _start: string | Date;

  get content(): string {
    return this._content;
  }

  set content(value: string) {
    this._content = value;
  }

  get cost(): number {
    return this._cost;
  }

  set cost(value: number) {
    this._cost = value;
  }

  get title(): string {
    return this._title;
  }

  set title(value: string) {
    this._title = value;
  }

  get end(): string | Date {
    return this._end;
  }

  set end(value: string | Date) {
    this._end = value;
  }

  get start(): string | Date {
    return this._start;
  }

  set start(value: string | Date) {
    this._start = value;
  }

  constructor(content = "", cost = 0, start = "", end = "", title = "") {
    this._content = content;
    this._cost = cost;
    this._title = title;
    this._start = start;
    this._end = end;
  }

  setAttr(event: any) {
    this._content = event.extendedProps.content;
    this._cost = event.extendedProps.cost;
    console.log(event.startStr);
    this._start = event.startStr;
    this._end = event.endStr;
    this._title = event.title;
  }

  resetAttr(): void {
    this._content = "";
    this._cost = 0;
    this._title = "";
    this._start = "";
    this._end = "";
  }
}

export default ScheduleContentDetail;
