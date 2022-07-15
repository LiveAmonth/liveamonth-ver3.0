export interface LoginType {
  loginId: string;
  password: string;
}

export interface FindIdType {
  email: string;
}

export interface FindPwType {
  loginId: string;
  email: string;
}

export interface SignUpType {
  loginId: string;
  password: string;
  passwordCheck: string;
  name: string;
  nickname: string;
  email: string;
  birth: string;
  gender: string;
}
