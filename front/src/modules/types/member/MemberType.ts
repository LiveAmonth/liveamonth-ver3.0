export interface TokenMemberInfoType {
  id: number;
  loginId: string;
  nickname: string;
}

export interface ProfileType {
  id: number;
  loginId: string;
  nickname: string;
  image: string;
  name: string;
  email: string;
  gender: string;
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
