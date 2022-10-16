import EditProfile from "@/components/member/management/EditProfile.vue";
import ChangePassword from "@/components/member/management/ChangePassword.vue";
import DropMember from "@/components/member/management/DropMember.vue";
import WriteInquiry from "@/components/member/management/WriteInquiry.vue";
import InquiryList from "@/components/member/management/InquiryList.vue";
import { useRouter } from "vue-router";
import { useMenuTab } from "@/composables/common/tabs";
import type {
  ProfileType,
  SimpleProfileType,
} from "@/modules/types/member/MemberTypes";
import type {
  CategoryMenuType,
  MyPageMenuType,
  NameIconType,
} from "@/modules/types/common/MenuTypes";
import type { DefineComponent } from "vue";

export const useMyPage = () => {
  const router = useRouter();
  const { getTabsItem, getMenuCategory } = useMenuTab();
  const accountMenu: CategoryMenuType<MyPageMenuType> = {
    category: getMenuCategory("myPage", "account", "User"),
    menus: [
      {
        name: "editProfile",
        component: EditProfile,
      },
      {
        name: "changePassword",
        component: ChangePassword,
      },
      {
        name: "dropMember",
        component: DropMember,
      },
    ],
  };
  const inquiryMenu: CategoryMenuType<MyPageMenuType> = {
    category: getMenuCategory("myPage", "inquiry", "Headset"),
    menus: [
      {
        name: "write",
        component: WriteInquiry,
      },
      {
        name: "answer",
        component: InquiryList,
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

  const goManagement = async (key: string) => {
    const object = JSON.parse(key);
    await router.push({
      name: "management",
      params: { category: object.category, menu: object.menu },
    });
  };

  const findComponent = (category: string, menu: string): DefineComponent => {
    const menus: MyPageMenuType[] =
      category === accountMenu.category.code
        ? accountMenu.menus
        : inquiryMenu.menus;
    let component: DefineComponent = EditProfile;
    menus.forEach((value) => {
      if (value.name == menu) component = value.component;
    });
    return component;
  };

  return {
    profileTabs,
    myPagePostsTabs,
    managementMenu: <CategoryMenuType<MyPageMenuType>[]>[
      accountMenu,
      inquiryMenu,
    ],
    getPostCount,
    goManagement,
    findComponent,
  };
};
