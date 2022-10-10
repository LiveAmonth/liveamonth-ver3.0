import http from "@/http-common";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/pagination/PaginationTypes";
import type { EnumType } from "@/modules/types/common/CommonTypes";
import type {
  InquiryType,
  InquiryEditor,
} from "@/modules/types/member/MemberTypes";

class InquiryApiService {
  async getInquiryCategory(): Promise<EnumType[]> {
    return await http
      .get("/categories/inquiry")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async writeInquiry(editor: InquiryEditor): Promise<string> {
    return await http
      .post("/inquiries", JSON.stringify(editor.getCreateData()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getInquiries(
    pageable: PageableRequestType
  ): Promise<PageableResponseType> {
    return await http
      .get(`/inquiries/list?page=${pageable.page - 1}&size=${pageable.size}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getInquiry(id: number): Promise<InquiryType> {
    return await http
      .get(`/inquiries/${id}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editInquiry(id: number, editor: InquiryEditor): Promise<string> {
    return await http
      .patch(`/inquiries/${id}`, JSON.stringify(editor.getEditData()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async deleteInquiry(id: number): Promise<string> {
    return await http
      .delete(`/inquiries/${id}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new InquiryApiService();
