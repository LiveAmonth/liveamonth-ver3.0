import { ref } from "vue";
import { useMemberStore } from "@/stores/member";

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
      isPending.value = false;
    } catch (err) {
      error.value = err;
      isPending.value = false;
    }
  };

  return {
    error,
    isPending,
    getGenderType,
  };
};
