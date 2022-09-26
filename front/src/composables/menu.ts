import { useMember } from "@/composables/member";
import { useAuth } from "@/composables/auth";
import type { MenuType } from "@/modules/types/common/MenuType";

export const useHeaderMenu = () => {
  const { isLoggedIn } = useAuth();
  const { simpleProfile } = useMember();

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
    { name: "review", sub: [], route: { name: "review" } },
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
  };
};
