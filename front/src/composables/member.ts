import { ref } from "vue";
import { useMemberStore } from "@/stores/member";
import MemberApiService from "@/services/MemberApiService";
import type { SignUpType } from "@/modules/types/form/FormType";

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

  const getPostCount = async (id: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getPostCount(id);
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
    getGenderType,
    getPostCount,
  };
};
