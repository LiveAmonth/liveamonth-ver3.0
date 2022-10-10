import http from "@/http-common";
import type { LoginType } from "@/modules/types/member/MemberTypes";
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

  async reissue(): Promise<TokenType> {
    return await http
      .post(
        "/auth/reissue",
        {},
        {
          withCredentials: true,
        }
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async logout(): Promise<void> {
    await http
      .get("/auth/logout", {
        withCredentials: true,
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new AuthApiService();
