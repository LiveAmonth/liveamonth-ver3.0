import EditProfile from "@/components/member/EditProfile.vue";
import EditPassword from "@/components/member/EditPassword.vue";
import { useRouter } from "vue-router";
import { tabs } from "@/composables/tabs";
import type {
  NameIconType,
  ManagementMenuType,
  SimpleProfileType,
  ProfileType,
} from "@/modules/types/member/MemberType";

export const useMyPage = () => {
  const router = useRouter();
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

  const goManagement = (key: string) => {
    const object = JSON.parse(key);
    router.push({
      name: "management",
      params: { category: object.category, menu: object.menu },
    });
  };

  return {
    profileTabs,
    myPagePostsTabs,
    managementMenu: [accountMenu, inquiryMenu],
    getPostCount,
    goManagement,
  };
};
