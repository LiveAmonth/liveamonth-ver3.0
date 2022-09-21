import EditProfile from "@/components/member/EditProfile.vue";
import EditPassword from "@/components/member/EditPassword.vue";
import { tabs } from "@/composables/tabs";
import type {
  NameIconType,
  ManagementMenuType,
  SimpleProfileType,
  ProfileType,
} from "@/modules/types/member/MemberType";

export const useMyPage = () => {
  const { getTabsItem, getMyPageMenuCategory } = tabs();
  const accountMenu: ManagementMenuType = {
    category: getMyPageMenuCategory("account", "User"),
    menus: [
      {
        value: "editProfile",
        component: EditProfile,
      },
      {
        value: "editPassword",
        component: EditPassword,
      },
      {
        value: "dropMember",
        component: EditProfile,
      },
    ],
  };
  const inquiryMenu: ManagementMenuType = {
    category: getMyPageMenuCategory("inquiry", "Headset"),
    menus: [
      {
        value: "write",
        component: EditProfile,
      },
      {
        value: "answer",
        component: EditProfile,
      },
    ],
  };
  const profileTabs: NameIconType[] = [
    getTabsItem("profile", "follower", "User"),
    getTabsItem("profile", "schedule", "Calendar"),
    getTabsItem("profile", "review", "Notebook"),
  ];
  const myPagePostsTabs: NameIconType[] = [
    getTabsItem("myPage", "schedule", "Calendar"),
    getTabsItem("myPage", "review", "Notebook"),
  ];

  const getPostCount = (
    post: string,
    profile: SimpleProfileType | ProfileType
  ) => {
    if (post === "follower") return profile.numberOfFollowers;
    if (post === "schedule") return profile.numberOfSchedules;
    if (post === "review") return profile.numberOfReviews;
    return null;
  };

  return {
    profileTabs,
    myPagePostsTabs,
    getPostCount,
    managementMenu: [accountMenu, inquiryMenu],
  };
};
