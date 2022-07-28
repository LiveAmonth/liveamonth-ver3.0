import http from "@/http-common";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  DuplicationCheckType,
  FindIdType,
  FindPwType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  PostCountType,
} from "@/modules/types/member/MemberType";

class MemberApiService {
  async getGenderTypes(): Promise<EnumType[]> {
    return await http
      .get("/categories/gender-type")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async duplicateCheck(
    field: string,
    param: string
  ): Promise<DuplicationCheckType> {
    return await http
      .get(`/members/exists/${field}/${param}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async signUp(request: SignUpType) {
    return await http
      .post("/members/sign-up", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getPostCount(id: number): Promise<PostCountType> {
    return await http
      .get(`/members/post-count/${id}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async findId(request: FindIdType): Promise<FoundIdType> {
    return await http
      .post("/members/find-id", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async findPw(request: FindPwType): Promise<void> {
    await http
      .post("/members/find-pw", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new MemberApiService();
