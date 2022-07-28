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
  birth: string;
  gender: string;
}

export interface PostCountType {
  numOfReviews: number;
  numOfSchedules: number;
  numOfFollowers: number;
}
