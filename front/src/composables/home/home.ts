import { useMember } from "@/composables/member/member";
import { computed } from "vue";
import { useMenuTab } from "@/composables/common/tabs";
import { useAuth } from "@/composables/member/auth";
import type { MenuType, NameIconType } from "@/modules/types/common/MenuTypes";
import { useCategory } from "@/composables/common/category";

export const useHome = () => {
  const { isLoggedIn } = useAuth();
  const { simpleProfile } = useMember();
  const { getTabsItem } = useMenuTab();
  const { reviewSearchType } = useCategory();
  const loggedPostsTabs: NameIconType[] = [
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
    getTabsItem("home", "followed", "User"),
  ];
  const homePostsTabs: NameIconType[] = [
    getTabsItem("home", "schedule", "Calendar"),
    getTabsItem("home", "review", "Notebook"),
  ];
  const mainMenus = computed((): MenuType[] => [
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
    {
      name: "review.title",
      route: {
        name: "review-list",
        params: { menu: reviewSearchType.value[0].code.toLowerCase() },
      },
      sub: [
        {
          name: "review.group.review",
          sub: [],
          route: {
            name: "review-list",
            params: { menu: reviewSearchType.value[0].code.toLowerCase() },
          },
        },
        {
          name: "review.group.etc",
          sub: [],
          route: {
            name: "review-list",
            params: {
              menu: reviewSearchType.value.slice(-1)[0].code.toLowerCase(),
            },
          },
        },
      ],
    },
    {
      name: "myPage",
      sub: [],
      route: isLoggedIn.value
        ? { name: "profile", params: { post: "schedule" } }
        : { name: "login" },
    },
  ]);

  return {
    mainMenus,
    homePostsTabs: computed(() =>
      isLoggedIn.value ? loggedPostsTabs : homePostsTabs
    ),
  };
};
