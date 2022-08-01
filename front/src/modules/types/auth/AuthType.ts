export interface TokenType {
  accessToken: string;
  refreshToken?: string;
  grantType: string;
}

export interface JWTType {
  auth: string;
  exp: number;
  sub: string;
}
