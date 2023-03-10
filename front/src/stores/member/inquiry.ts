import InquiryApiService from "@/services/member/InquiryApiService";
import { defineStore } from "pinia";
import type {
  InquiryAnswerType,
  InquiryListType,
  InquiryType,
  InquiryEditor,
} from "@/modules/types/member/MemberTypes";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/pagination/PaginationTypes";
import type { PageableType } from "@/modules/types/pagination/PaginationTypes";
import type { EnumType } from "@/modules/types/common/CommonTypes";

export const useInquiryStore = defineStore("inquiry", {
  state: () => ({
    inquiryCategory: {} as EnumType[],
    inquiryAnswer: {} as InquiryAnswerType,
    pageableInquiries: {} as PageableResponseType,
    currInquiry: {} as InquiryType,
  }),
  getters: {
    inquiries: (state): InquiryListType[] =>
      state.pageableInquiries.content as InquiryListType[],
    inquiryPage: (state): PageableType => state.pageableInquiries.pageable,
  },
  actions: {
    getInquiryCategory: async function () {
      await InquiryApiService.getInquiryCategory()
        .then((response: EnumType[]) => {
          this.inquiryCategory = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    writeInquiry: async function (request: InquiryEditor) {
      await InquiryApiService.writeInquiry(request)
        .then((response: string) => {
          console.log(response);
        })
        .catch((error) => {
          throw error;
        });
    },

    getInquiries: async function (pageable: PageableRequestType) {
      await InquiryApiService.getInquiries(pageable)
        .then((response: PageableResponseType) => {
          this.pageableInquiries = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getInquiry: async function (id: number) {
      await InquiryApiService.getInquiry(id)
        .then((response: InquiryType) => {
          this.currInquiry = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editInquiry: async function (inquiryId: number, form: InquiryEditor) {
      await InquiryApiService.editInquiry(inquiryId, form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    deleteInquiry: async function (inquiryId: number) {
      await InquiryApiService.deleteInquiry(inquiryId)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
