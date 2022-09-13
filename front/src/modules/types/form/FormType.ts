import type {
  DatePeriodType,
  DateTimePeriodType,
  ScheduleCardType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import type { EventApi } from "@fullcalendar/common";

export interface LoginType {
  loginId: string;
  password: string;

  [key: string]: string;
}

export interface FindIdType {
  name: string;
  email: string;

  [key: string]: string;
}

export interface FindPwType {
  loginId: string;
  email: string;

  [key: string]: string;
}

export interface SignUpType {
  loginId: string;
  password: string;
  passwordCheck: string;
  name: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;

  [key: string]: string;
}

export interface SignUpCheckType {
  loginId: boolean;
  nickname: boolean;
  email: boolean;

  [key: string]: boolean;
}

export interface DuplicationCheckType {
  isAvailable: boolean;
  value: string;
  message: string;
}

export interface ScheduleContentFormType {
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;

  setAttr(event: EventApi): void;
  setForm(data: ScheduleContentType): void;
  clear(date: string): void;
}

export interface ScheduleFormType {
  title: string;
  publicFlag: boolean;
  city: string;
  period: DatePeriodType;

  setForm(data: ScheduleCardType): void;
  clear(): void;
  getObject(): any;
}

export interface CommentFormType {
  comment: string;
}
