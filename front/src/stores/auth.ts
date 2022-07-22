import { defineStore } from "pinia";
import jwtDecode from "jwt-decode";
import AuthApiService from "@/services/AuthApiService";
import type { LoginType } from "@/modules/types/form/FormType";
import type { JWTType, TokenType } from "@/modules/types/auth/AuthType";
import type { ProfileType } from "@/modules/types/member/MemberType";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("access-token") || "",
    grantType: "Baerer",
    loggedIn: false,
  }),
  getters: {
    profile: (state): ProfileType => {
      if (state.token != "") {
        const jwt: JWTType = jwtDecode(state.token);
        return jwt.profile;
      } else {
        return {} as ProfileType;
      }
    },
    expireTime: (state): number | null => {
      if (state.token != "") {
        const jwt: JWTType = jwtDecode(state.token);
        return jwt.exp;
      } else {
        return null;
      }
    },
  },
  actions: {
    async login(data: LoginType) {
      await AuthApiService.login(data)
        .then((response: TokenType) => {
          this.token = response.accessToken;
          this.loggedIn = true;
          localStorage.setItem("access-token", response.accessToken);
        })
        .catch((error) => {
          throw error;
        });
    },
    async logout() {
      await AuthApiService.logout(this.grantType, this.token)
        .then(() => {
          this.token = "";
          this.loggedIn = false;
          localStorage.removeItem("access-token");
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
