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
  name: string;
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
