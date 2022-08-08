import type { EnumType } from "@/modules/types/common/EnumType";
import type { ScheduleProfileType } from "@/modules/types/member/MemberType";

export interface ScheduleContentType {
  date: string | Date;
  description: string;
}

export interface ScheduleType {
  contents: ScheduleContentType[];
  price: number;
  city: EnumType;
  hits: number;
  likes: number;
  profile: ScheduleProfileType;
  range: DateRangeType;
}

export interface DateRangeType {
  start: Date | string;
  end: Date | string;
}

export interface calendarAttrType {
  date: Date;
  popover: {
    label: string;
  };
  highlight: string;
  customDate: ScheduleContentType[];
}
