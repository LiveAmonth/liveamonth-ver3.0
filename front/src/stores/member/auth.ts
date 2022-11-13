import AuthApiService from "@/services/member/AuthApiService";
import jwtDecode from "jwt-decode";
import { defineStore } from "pinia";
import type { LoginType } from "@/modules/types/member/MemberTypes";
import type { JWTType, TokenType } from "@/modules/types/auth/AuthType";
import type { InitDataType } from "@/modules/types/common/CommonTypes";

const storageToken: TokenType = localStorage["token-info"]
  ? JSON.parse(localStorage["token-info"])
  : null;
const initTokenInfo: InitDataType = storageToken
  ? { state: true, data: storageToken }
  : { state: false, data: {} as TokenType };

export const useAuthStore = defineStore("auth", {
  state: () => ({
    tokenInfo: initTokenInfo as InitDataType,
  }),
  getters: {
    accessToken: (state): string =>
      (state.tokenInfo.data as TokenType).accessToken,
    grantTye: (state): string => (state.tokenInfo.data as TokenType).grantType,
    isExpired: (state): boolean => {
      if (state.tokenInfo.state) {
        const token = (state.tokenInfo.data as TokenType).accessToken;
        const jwt: JWTType = jwtDecode(token);
        return Date.now() >= jwt.exp * 1000;
      } else {
        return false;
      }
    },
    loggedIn: (state): boolean => state.tokenInfo.state,
  },
  actions: {
    login: async function (data: LoginType) {
      await AuthApiService.login(data)
        .then((response: TokenType) => {
          localStorage.setItem("token-info", JSON.stringify(response));
          this.tokenInfo.state = true;
          this.tokenInfo.data = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    logout: async function () {
      await AuthApiService.logout()
        .then(() => {
          localStorage.removeItem("token-info");
          this.tokenInfo.state = false;
          this.tokenInfo.data = {} as TokenType;
        })
        .catch((error) => {
          throw error;
        });
    },

    reissue: async function () {
      await AuthApiService.reissue()
        .then((response: TokenType) => {
          localStorage.setItem("token-info", JSON.stringify(response));
          this.tokenInfo.state = true;
          this.tokenInfo.data = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
