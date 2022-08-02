import { ref } from "vue";
import { useMemberStore } from "@/stores/member";
import MemberApiService from "@/services/MemberApiService";
import type {
  FindIdType,
  FindPwType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type { TokenType } from "@/modules/types/auth/AuthType";

export const useMember = () => {
  const store = useMemberStore();
  const error = ref();
  const isPending = ref(false);

  const getGenderType = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getGenderType();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const signUp = async (request: SignUpType) => {
    error.value = null;
    isPending.value = true;
    try {
      await MemberApiService.signUp(request);
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
      await MemberApiService.findPw(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };
  const getMember = async (request: TokenType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getMember(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };
  const getSimpleProfile = async (request: TokenType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getSimpleProfile(request);
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
    signUp,
    findId,
    findPw,
    getGenderType,
    getMember,
    getSimpleProfile,
  };
};
