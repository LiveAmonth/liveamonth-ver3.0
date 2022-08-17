import type { EnumType } from "@/modules/types/common/EnumType";
import type { SimpleProfileType } from "@/modules/types/member/MemberType";
import type { SearchCondType } from "../common/SearchType";

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
  period: DateRangeType;
}

export interface ScheduleContentType {
  id?: number;
  title: string;
  content: string;
  cost: number;
  date: string;
}

export interface DateRangeType {
  startDate: string | Date;
  endDate: string | Date;
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
