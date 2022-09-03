import type { EnumType } from "@/modules/types/common/EnumType";
import type { SimpleProfileType } from "@/modules/types/member/MemberType";
import type { SearchCondType } from "../common/SearchType";
import type { CommentType } from "@/modules/types/comment/CommentTypes";

export interface ScheduleDetailType {
  card: ScheduleCardType;
  contents: ScheduleContentType[];
}

export interface ScheduleCardType {
  id: number;
  title: string;
  profile: SimpleProfileType;
  cost: number;
  city: EnumType;
  hits: number;
  likes: number;
  comments: number;
  period: DatePeriodType;
  comment: CommentType | null;
  publicFlag: boolean;
}
export interface ScheduleSimpleCardType {
  id: number;
  title: string;
  cost: number;
  city: EnumType;
  hits: number;
  likes: number;
  comments: number;
  period: DatePeriodType;
}

export interface ScheduleContentType {
  id: number;
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

export interface DatePeriodType {
  startDate: string | Date;
  endDate: string | Date;
}
export interface DateTimePeriodType {
  startDateTime: string | Date;
  endDateTime: string | Date;
}

export interface ScheduleSearchType extends SearchCondType {
  memberNickname: string | null;
  cityName: string | null;
  startDate: string | null;
  title: string | null;
}

export interface CalendarType {
  title: string;
  start: string;
  end: string;
  allDay: boolean;
  extendedProps: CalendarExtendedType;
}

export interface CalendarExtendedType {
  cost: number;
  content: string;
}
