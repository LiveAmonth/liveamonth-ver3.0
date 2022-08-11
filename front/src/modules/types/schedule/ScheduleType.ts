import type { EnumType } from "@/modules/types/common/EnumType";
import type { SimpleProfileType } from "@/modules/types/member/MemberType";

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
  title: string;
  content: string;
  cost: number;
  date: string;
}

export interface DateRangeType {
  startDate: string | Date;
  endDate: string | Date;
}

export interface ScheduleSearchType {
  memberNickname: string | null;
  cityName: string | null;
  startDate: string | null;
}
