import { tabs } from "@/composables/tabs";
import type { NameIconType } from "@/modules/types/member/MemberType";

export const useHome = () => {
  const { getTabsItem } = tabs();

  const homePostsTabs: NameIconType[] = [
    getTabsItem("home", "followed", "User"),
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
  ];

  return {
    homePostsTabs,
  };
};
