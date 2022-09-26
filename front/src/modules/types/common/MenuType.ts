import type { RouteLocationRaw } from "vue-router";

export interface MenuType {
  name: string;
  route: RouteLocationRaw;
  sub: MenuType[];
}
