import type { EnumType } from "@/modules/types/common/EnumType";
import type { SimpleProfileType } from "@/modules/types/member/MemberType";

export interface ScheduleContentType {
  date: string | Date;
  title: string;
}

export interface ScheduleType {
  contents: ScheduleContentType[];
  price: number;
  city: EnumType;
  hits: number;
  likes: number;
  profile: SimpleProfileType;
  range: DateRangeType;
}

export interface DateRangeType {
  start: Date | string;
  end: Date | string;
}

export interface calendarAttrType {
  date: Date;
  popover: boolean;
  highlight: string;
  customDate: ScheduleContentType[];
}
