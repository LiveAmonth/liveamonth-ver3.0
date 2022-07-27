import type { TokenMemberInfoType } from "@/modules/types/member/MemberType";

export interface TokenType {
  accessToken: string;
  refreshToken?: string;
  grantType: string;
}

export interface JWTType {
  auth: string;
  exp: number;
  profile: TokenMemberInfoType;
  sub: string;
}
