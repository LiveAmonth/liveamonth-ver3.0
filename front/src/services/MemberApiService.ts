import http from "@/http-common";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  ConfirmFormType,
  FindIdType,
  FindPwType,
  ReconfirmType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  ProfileType,
  SimpleProfileType,
} from "@/modules/types/member/MemberType";
import type ProfileEditor from "@/modules/class/member/ProfileEditor";
import type ChangePasswordEditor from "@/modules/class/member/ChangePasswordEditor";

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

  async duplicateCheck(field: string, param: string): Promise<ConfirmFormType> {
    return await http
      .get(`/members/exists/${field}/${param}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async reconfirm(request: ReconfirmType): Promise<ConfirmFormType> {
    return await http
      .post("/members/reconfirm", JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async signUp(request: SignUpType): Promise<string> {
    return await http
      .post("/members/sign-up", JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
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
      .patch(`/members/password`, JSON.stringify(request.getRequest()))
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

  async getMember(request: string): Promise<ProfileType> {
    return await http
      .get("/members/profile/", {
        headers: {
          Authorization: request,
        },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getSimpleProfile(request: string): Promise<SimpleProfileType> {
    return await http
      .get("/members/profile/simple", {
        headers: {
          Authorization: request,
        },
      })
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
}

export default new MemberApiService();
