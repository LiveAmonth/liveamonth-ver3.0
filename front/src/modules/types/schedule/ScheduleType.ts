import type { EnumType } from "@/modules/types/common/EnumType";
import type { SimpleProfileType } from "@/modules/types/member/MemberType";
import type { SearchCondType } from "../common/SearchType";
import type { CommentType } from "@/modules/types/comment/CommentResponse";

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
}

export interface MyScheduleCardType {
  id: number;
  memberId: number;
  title: string;
  city: EnumType;
  cost: number;
  hits: number;
  likes: number;
  period: DatePeriodType;
  publicFlag: boolean;
}

export interface ScheduleContentType {
  id: number;
  title: string;
  content: string;
  cost: number;
  timePeriod: DateTimePeriodType;
}

export interface DatePeriodType {
  startDate: string;
  endDate: string;
}
export interface DateTimePeriodType {
  startDateTime: string;
  endDateTime: string;
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
