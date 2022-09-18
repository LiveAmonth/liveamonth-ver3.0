import { computed, ref } from "vue";
import { useMemberStore } from "@/stores/member";
import { useAuth } from "@/composables/auth";
import type {
  FindIdType,
  FindPwType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type {
  ManagementMenuCatType,
  ManagementMenuType,
  ProfileType,
  SimpleProfileType,
} from "@/modules/types/member/MemberType";

export const useMember = () => {
  const store = useMemberStore();
  const error = ref();
  const isPending = ref(false);
  const { bearerToken } = useAuth();

  const simpleProfile = computed((): SimpleProfileType => store.simpleProfile);
  const memberProfile = computed((): ProfileType => store.memberProfile);
  const accountCat: ManagementMenuCatType = { cat: "account", icon: "User" };
  const inquiryCat: ManagementMenuCatType = { cat: "inquiry", icon: "Headset" };
  const accountMenu: ManagementMenuType[] = [
    {
      category: accountCat,
      value: "editProfile",
    },
    {
      category: accountCat,
      value: "editPassword",
    },
    {
      category: accountCat,
      value: "dropMember",
    },
  ];
  const inquiryMenu: ManagementMenuType[] = [
    {
      category: inquiryCat,
      value: "write",
    },
    {
      category: inquiryCat,
      value: "answer",
    },
  ];
  const signUp = async (request: SignUpType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.signUp(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const findId = async (request: FindIdType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.findId(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const findPw = async (request: FindPwType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.findPw(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getMember = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getMember(bearerToken.value);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getSimpleProfile = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getSimpleProfile(bearerToken.value);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  return {
    error,
    isPending,
    simpleProfile,
    memberProfile,
    managementMenu: [accountMenu, inquiryMenu],
    signUp,
    findId,
    findPw,
    getMember,
    getSimpleProfile,
  };
};
