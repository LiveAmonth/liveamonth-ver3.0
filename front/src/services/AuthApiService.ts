import http from "@/http-common";
import type { LoginType } from "@/modules/types/form/FormType";
import type { TokenType } from "@/modules/types/auth/AuthType";
import { useCookies } from "vue3-cookies";

class AuthApiService {
  async login(request: LoginType): Promise<TokenType> {
    return await http
      .post("/auth/login", JSON.stringify(request))
      .then((response) => {
        const { cookies } = useCookies();
        console.log(cookies.keys());
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
  async logout(grantType: string, token: string): Promise<void> {
    await http
      .post(
        "/auth/logout",
        {},
        {
          headers: {
            Authorization: `${grantType} ${token}`,
          },
        }
      )
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new AuthApiService();
