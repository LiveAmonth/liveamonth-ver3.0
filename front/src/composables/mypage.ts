import EditProfile from "@/components/member/EditProfile.vue";
import EditPassword from "@/components/member/EditPassword.vue";
import type {
  ManagementMenuCatType,
  ManagementMenuType,
} from "@/modules/types/member/MemberType";

export const useMyPage = () => {
  const accountCat: ManagementMenuCatType = { name: "account", icon: "User" };
  const inquiryCat: ManagementMenuCatType = {
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

  const findComponent = (category: string) => {

  };

  return {
    managementMenu: [accountMenu, inquiryMenu],
  };
};
