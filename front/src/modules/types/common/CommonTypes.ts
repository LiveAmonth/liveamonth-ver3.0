import type { FormRules } from "element-plus/es";

export interface IdResponseType {
  id: number;
}

export interface EnumType {
  code: string;
  value: string;
}

export interface FormType<T> {
  setForm(data: T): void;

  getRules(): FormRules;

  clear(): void;

  getCreateData(): unknown;

  getEditData(): unknown;
}

export interface ImageContentType {
  content: string;
  image: string;
}

export interface InitDataType {
  state: boolean;
  data: object;
}

export interface CheckType {
  result: boolean;
  message: string;
}
