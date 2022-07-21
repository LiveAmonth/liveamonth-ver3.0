import http from "@/http-common";
import type { LoginType } from "@/modules/types/form/FormType";
import type { TokenType } from "@/modules/types/auth/AuthType";

class AuthApiService {
  async login(request: LoginType): Promise<TokenType> {
    return await http
      .post("/auth/login", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        console.log(error);
      });
  }
}

export default new AuthApiService();
