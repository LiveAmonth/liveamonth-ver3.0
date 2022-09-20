import { computed, ref } from "vue";
import { useMemberStore } from "@/stores/member";
import { useAuth } from "@/composables/auth";
import type {
  FindIdType,
  FindPwType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
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

  const foundId = computed((): FoundIdType => store.foundId);

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
    foundId,
    signUp,
    findId,
    findPw,
    getMember,
    getSimpleProfile,
  };
};
