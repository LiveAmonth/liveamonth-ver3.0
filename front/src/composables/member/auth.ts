import router from "@/router";
import { computed, ref } from "vue";
import { useAuthStore } from "@/stores/member/auth";
import { useMemberStore } from "@/stores/member/member";
import type { LoginType } from "@/modules/types/member/MemberTypes";

export const useAuth = () => {
  const store = useAuthStore();
  const memberStore = useMemberStore();
  const error = ref();
  const isPending = ref(false);
  const isLoggedIn = computed(() => store.loggedIn);
  const bearerToken = computed((): string => {
    return `${store.grantTye} ${store.accessToken}`;
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
      memberStore.$reset();
      await store.logout();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const logoutBtn = async () => {
    await logout().then(() => {
      if (!isLoggedIn.value) {
        router.push({ name: "login" });
      }
    });
  };

  const reissue = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.reissue();
      if (!memberStore.simpleProfile.id) {
        store.clearAuth();
      }
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
    bearerToken,
    login,
    logout,
    logoutBtn,
    reissue,
  };
};
