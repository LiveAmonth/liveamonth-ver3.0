import { useMember } from "@/composables/member/member";
import { computed } from "vue";
import { useTab } from "@/composables/common/tabs";
import { useAuth } from "@/composables/member/auth";
import type { MenuType } from "@/modules/types/common/MenuType";
import type { NameIconType } from "@/modules/types/member/MemberType";

export const useHome = () => {
  const { isLoggedIn } = useAuth();
  const { simpleProfile } = useMember();
  const { getTabsItem } = useTab();

  const loggedPostsTabs: NameIconType[] = [
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
    getTabsItem("home", "followed", "User"),
  ];
  const homePostsTabs: NameIconType[] = [
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
  ];
  const mainMenus: MenuType[] = [
    { name: "city", route: { name: "city" }, sub: [] },
    {
      name: "schedule",
      route: { name: "schedule-list" },
      sub: [
        { name: "otherSchedule", sub: [], route: { name: "schedule-list" } },
        {
          name: "mySchedule",
          sub: [],
          route: isLoggedIn.value
            ? {
                name: "my-schedule",
                params: { loginId: simpleProfile.value.loginId },
              }
            : { name: "login" },
        },
      ],
    },
    { name: "review", sub: [], route: { name: "review-list" } },
    {
      name: "myPage",
      sub: [],
      route: isLoggedIn.value
        ? { name: "profile", params: { post: "schedule" } }
        : { name: "login" },
    },
  ];

  return {
    mainMenus,
    homePostsTabs: computed(() =>
      isLoggedIn.value ? loggedPostsTabs : homePostsTabs
    ),
  };
};
