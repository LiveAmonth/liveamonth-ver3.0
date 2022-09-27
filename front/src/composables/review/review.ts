import { useMenuTab } from "@/composables/common/tabs";
import type {
  MenuType,
  CategoryMenuType,
} from "@/modules/types/common/MenuType";

export const useReview = () => {
  const { getMenuCategory } = useMenuTab();
  const placeCategoryMenu: CategoryMenuType<MenuType> = {
    category: getMenuCategory("review", "place", "Place"),
    menus: [
      {
        name: "se",
        sub: [],
        route: { name: "review-list", params: { menu: "se" } },
      },
      {
        name: "gn",
        sub: [],
        route: { name: "review-list", params: { menu: "gn" } },
      },
      {
        name: "gj",
        sub: [],
        route: { name: "review-list", params: { menu: "gj" } },
      },
      {
        name: "bs",
        sub: [],
        route: { name: "review-list", params: { menu: "bs" } },
      },
      {
        name: "ys",
        sub: [],
        route: { name: "review-list", params: { menu: "ys" } },
      },
      {
        name: "jj",
        sub: [],
        route: { name: "review-list", params: { menu: "jj" } },
      },
      {
        name: "other",
        sub: [],
        route: { name: "review-list", params: { menu: "other" } },
      },
    ],
  };
  const etcMenus: CategoryMenuType<MenuType> = {
    category: getMenuCategory("review", "etc", "Paperclip"),
    menus: [
      {
        name: "etc",
        sub: [],
        route: { name: "review-list", params: { menu: "etc" } },
      },
    ],
  };
  return {
    reviewMenus: <CategoryMenuType<MenuType>[]>[placeCategoryMenu, etcMenus],
  };
};
