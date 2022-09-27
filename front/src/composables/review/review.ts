import { useMenuTab } from "@/composables/common/tabs";
import type {
  MenuType,
  NameIconType,
  CategoryMenuType,
} from "@/modules/types/common/MenuType";
import { ref } from "vue";

export const useReview = () => {
  const { getTabsItem, getMenuCategory } = useMenuTab();

  const isPending = ref<boolean>(false);
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
  const reviewSearchTabs: NameIconType[] = [
    getTabsItem("review", "total", ""),
    getTabsItem("review", "review", ""),
    getTabsItem("review", "question", ""),
  ];

  return {
    isPending,
    reviewMenus: <CategoryMenuType<MenuType>[]>[placeCategoryMenu, etcMenus],
    reviewSearchTabs,
  };
};
