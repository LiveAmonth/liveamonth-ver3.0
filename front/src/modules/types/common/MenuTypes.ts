import type { RouteLocationRaw } from "vue-router";
import type { Component } from "vue";
import { DefineComponent } from "vue";

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
  menus: T[];
}

export interface MyPageMenuType {
  value: string;
  component: DefineComponent;
}
