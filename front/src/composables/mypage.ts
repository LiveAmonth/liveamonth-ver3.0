import EditProfile from "@/components/member/EditProfile.vue";
import EditPassword from "@/components/member/EditPassword.vue";
import type {
  NameIconType,
  ManagementMenuType,
  SimpleProfileType,
  ProfileType,
} from "@/modules/types/member/MemberType";

export const useMyPage = () => {
  const accountCat: NameIconType = { name: "account", icon: "User" };
  const inquiryCat: NameIconType = {
    name: "inquiry",
    icon: "Headset",
  };
  const accountMenu: ManagementMenuType = {
    category: accountCat,
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
    category: inquiryCat,
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
    { name: "follower", icon: "User" },
    { name: "schedule", icon: "Calendar" },
    { name: "review", icon: "Notebook" },
  ];

  const myPagePostsTabs: NameIconType[] = [
    { name: "followed", icon: "User" },
    { name: "schedule", icon: "Calendar" },
    { name: "review", icon: "Notebook" },
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
