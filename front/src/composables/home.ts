import { tabs } from "@/composables/tabs";
import type { NameIconType } from "@/modules/types/member/MemberType";
import { useAuth } from "@/composables/auth";
import { computed } from "vue";

export const useHome = () => {
  const { getTabsItem } = tabs();
  const { isLoggedIn } = useAuth();

  const loggedPostsTabs: NameIconType[] = [
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
    getTabsItem("home", "followed", "User"),
  ];
  const homePostsTabs: NameIconType[] = [
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
  ];

  return {
    homePostsTabs: computed(() =>
      isLoggedIn.value ? loggedPostsTabs : homePostsTabs
    ),
  };
};
