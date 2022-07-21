import http from "@/http-common";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  DuplicationCheckType,
  SignUpType,
} from "@/modules/types/form/FormType";

class MemberApiService {
  async getGenderTypes(): Promise<EnumType[]> {
    return await http.get("/categories/gender-type").then((response) => {
      return response.data.data;
    });
  }

  async duplicateCheck(
    field: string,
    param: string
  ): Promise<DuplicationCheckType> {
    console.log("field:", field);
    return await http
      .get(`/members/exists/${field}/${param}`)
      .then((response) => {
        return response.data.data;
      });
  }

  async signUp(request: SignUpType) {
    return await http
      .post("/members/sign-up", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        console.log(error);
      });
  }
}

export default new MemberApiService();
