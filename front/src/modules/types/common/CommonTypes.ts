import type { FormRules } from "element-plus/es";

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

export interface initDataType {
  state: boolean;
  data: object;
}
