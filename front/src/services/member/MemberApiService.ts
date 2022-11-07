import http from "@/http-common";
import type { CheckType, EnumType } from "@/modules/types/common/CommonTypes";
import type {
  FindIdType,
  FindPwType,
  ReconfirmType,
  FoundIdType,
  ProfileType,
  SimpleProfileType,
  ChangePasswordEditor,
  ProfileEditor,
  MemberCreate,
} from "@/modules/types/member/MemberTypes";

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

  async duplicateCheck(field: string, param: string): Promise<CheckType> {
    return await http
      .get(`/members/exists/${field}/${param}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async reconfirm(request: ReconfirmType): Promise<CheckType> {
    return await http
      .post("/members/reconfirm", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async signUp(request: MemberCreate): Promise<string> {
    return await http
      .post("/members/sign-up", JSON.stringify(request.getCreateData()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error;
      });
  }

  async edit(request: ProfileEditor): Promise<string> {
    return await http
      .patch(`/members/profile`, JSON.stringify(request.getRequest()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async changePassword(request: ChangePasswordEditor): Promise<string> {
    return await http
      .patch(`/members/password`, JSON.stringify(request.getEditData()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async dropMember(): Promise<string> {
    return await http
      .post(`/members/drop`, {})
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getMember(): Promise<ProfileType> {
    return await http
      .get("/members/profile/")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getSimpleProfile(): Promise<SimpleProfileType> {
    return await http
      .get("/members/profile/simple")
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

  async findPw(request: FindPwType) {
    await http
      .post("/members/find-pw", JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editProfileImage(request: FormData): Promise<void> {
    await http
      .patch("/members/profile/image", JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new MemberApiService();
