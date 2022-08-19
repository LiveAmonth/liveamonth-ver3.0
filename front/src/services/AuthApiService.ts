import http from "@/http-common";
import type { LoginType } from "@/modules/types/form/FormType";
import type { TokenType } from "@/modules/types/auth/AuthType";

class AuthApiService {
  async login(request: LoginType): Promise<TokenType> {
    return await http
      .post("/auth/login", JSON.stringify(request), {
        withCredentials: true,
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async reissue(grantType: string, token: string): Promise<TokenType> {
    return await http
      .post(
        "/auth/reissue",
        {},
        {
          withCredentials: true,
          headers: {
            Authorization: `${grantType} ${token}`,
          },
        }
      )
      .then((response) => {
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
          withCredentials: true,
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
