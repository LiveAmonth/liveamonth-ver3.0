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
  numberOfReview: string;
  numberOfSchedule: string;
  numberOfFollower: string;
}

export interface SimpleProfileType {
  id: number;
  loginId: string;
  nickname: string;
  numOfReviews: number;
  numOfSchedules: number;
  numOfFollowers: number;
}

export interface FoundIdType {
  loginId: string;
  created: string;
}
