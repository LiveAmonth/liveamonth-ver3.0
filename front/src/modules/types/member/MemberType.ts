import type { EnumType } from "@/modules/types/common/EnumType";

export interface ProfileType {
  id: number;
  loginId: string;
  nickname: string;
  image: string;
  name: string;
  email: string;
  gender: EnumType;
  birth: string;
  numberOfReviews: number;
  numberOfSchedules: number;
  numberOfFollowers: number;
  numberOfFollows: number;
}

export interface SimpleProfileType {
  id: number;
  loginId: string;
  nickname: string;
  image: string;
  numberOfReviews: number;
  numberOfSchedules: number;
  numberOfFollowers: number;
  numberOfFollows: number;
}

export interface FoundIdType {
  loginId: string;
  created: string;
}

export interface NameIconType {
  code: string;
  value: string;
  icon: string;
}
export interface ManagementMenuType {
  category: NameIconType;
  menus: MenuType[];
}

export interface MenuType {
  value: string;
  component: object;
}

export interface InquiryListType {
  id: number;
  title: string;
  writer: string;
  category: EnumType;
  isAnswered: boolean;
  date: string;
}

export interface InquiryType {
  id: number;
  title: string;
  writer: string;
  content: string;
  category: EnumType;
  isAnswered: boolean;
  dateTime: string;
  answer: InquiryAnswerType;
}

export interface InquiryAnswerType {
  title: string;
  writer: string;
  content: string;
  dateTime: string;
}
