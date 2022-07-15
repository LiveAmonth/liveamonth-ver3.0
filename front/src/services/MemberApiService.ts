import http from "@/http-common";
import type { EnumType } from "@/modules/types/common/EnumType";

class MemberApiService {
  async getGenderTypes(): Promise<EnumType[]> {
    return await http.get("/categories/gender-type").then((response) => {
      return response.data.data;
    });
  }
}

export default new MemberApiService();
