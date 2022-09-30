import type { RouteLocationRaw } from "vue-router";

export interface MenuType {
  name: string;
  route: RouteLocationRaw;
  sub: MenuType[];
}

export interface NameIconType {
  code: string;
  value: string;
  icon: string;
}

export interface CategoryMenuType<T> {
  category: NameIconType;
  menus: T[] | undefined;
}

export interface MyPageMenuType {
  value: string;
  component: object;
}
