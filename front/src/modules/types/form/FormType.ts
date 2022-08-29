import type { DatePeriodType } from "@/modules/types/schedule/ScheduleType";

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
  start: string | Date;
  end: string | Date;

  setAttr(event: any): void;

  resetAttr(): void;
}

export interface ScheduleFormType {
  title: string;
  publicFlag: boolean;
  city: string;
  range: DatePeriodType;
}

export interface CommentFormType {
  comment: string;
}
