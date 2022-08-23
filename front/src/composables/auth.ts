import { computed, ref } from "vue";
import type { LoginType } from "@/modules/types/form/FormType";
import { useAuthStore } from "@/stores/auth";
import type { TokenType } from "@/modules/types/auth/AuthType";

export const useAuth = () => {
  const store = useAuthStore();
  const error = ref();
  const isPending = ref(false);
  const isLoggedIn = computed(() => store.loggedIn);
  const tokenInfo = computed(() => store.accessToken);

  const getTokenInfo = computed(() => {
    return {
      accessToken: store.accessToken,
      grantType: store.grantTye,
    } as TokenType;
  });

  const login = async (request: LoginType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.login(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };
  const logout = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.logout();
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
    isLoggedIn,
    getTokenInfo,
    login,
    logout,
  };
};
