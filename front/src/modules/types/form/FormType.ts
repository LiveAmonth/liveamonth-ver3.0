export interface LoginType {
  loginId: string;
  password: string;
  [key: string]: string;
}

export interface FindIdType {
  name: string;
  email: string;
  [key: string]: string;
}

export interface FindPwType {
  loginId: string;
  email: string;
  [key: string]: string;
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

  [key: string]: string;
}

export interface SignUpCheckType {
  loginId: boolean;
  nickname: boolean;
  email: boolean;

  [key: string]: boolean;
}

export interface DuplicationCheckType {
  isAvailable: boolean;
  value: string;
  message: string;
}
