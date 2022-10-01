import type {
  DatePeriodType,
  DateTimePeriodType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import type { FormRules } from "element-plus/es";
import type { InquiryType } from "@/modules/types/member/MemberType";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleResponse";

export interface FormType<T> {
  setForm(data: T): void;

  getRules(): FormRules;

  clear(): void;

  getCreateDate(): unknown;

  getEditDate(): unknown;
}

export interface LoginType {
  loginId: string;
  password: string;

  [key: string]: string;
}

export interface ReconfirmType {
  password: string;
}

export interface ChangePasswordType {
  password: string;
  passwordCheck: string;
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

export interface SignUpCheckType {
  loginId: boolean;
  nickname: boolean;
  email: boolean;

  [key: string]: boolean;
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
  checkForm: SignUpCheckType;

  [key: string]: any;
}

export interface EditProfileType {
  loginId: string;
  name: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;
  image: string;
  checkForm: SignUpCheckType;

  [key: string]: any;
}

export interface ConfirmFormType {
  isAvailable: boolean;
  value: string;
  message: string;
}

export interface ScheduleContentFormType extends FormType<ScheduleContentType> {
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

export interface ScheduleFormType extends FormType<ScheduleCardType> {
  title: string;
  publicFlag: boolean;
  city: string;
  period: DatePeriodType;
}

export interface WriteInquiryFormType extends FormType<InquiryType> {
  title: string;
  category: string;
  content: string;
  writer: string;
}
