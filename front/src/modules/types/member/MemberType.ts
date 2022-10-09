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
  numberOfReviews: string;
  numberOfSchedules: string;
  numberOfFollowers: string;
  numberOfFollows: string;
}

export interface SimpleProfileType {
  id: number;
  loginId: string;
  nickname: string;
  image: string;
  numberOfReviews: string;
  numberOfSchedules: string;
  numberOfFollowers: string;
  numberOfFollows: string;
}

export interface FoundIdType {
  loginId: string;
  created: string;
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
