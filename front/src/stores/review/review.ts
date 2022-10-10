import { defineStore } from "pinia";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/pagination/PaginationTypes";
import ReviewApiService from "@/services/review/ReviewApiService";
import { ReviewSearchCond } from "@/modules/types/review/ReviewTypes";
import type { ReviewListType } from "@/modules/types/review/ReviewTypes";

export const useReviewStore = defineStore("review", {
  state: () => ({
    searchCond: new ReviewSearchCond(),
    pageableReviews: {} as PageableResponseType,
    currReview: {} as object,
  }),
  getters: {
    otherReviews: (state): ReviewListType[] =>
      state.pageableReviews.content as ReviewListType[],
    reviewPage: (state): PageableType => state.pageableReviews.pageable,
  },
  actions: {
    addReview: async function (memberId: number, form: any) {
      await ReviewApiService.addReview(memberId, form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editReview: async function (reviewId: number, form: any) {
      await ReviewApiService.editReview(reviewId, form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    deleteReview: async function (reviewId: number) {
      await ReviewApiService.deleteReview(reviewId)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getReviews: async function (pageable: PageableRequestType) {
      await ReviewApiService.getReviews(this.searchCond, pageable)
        .then((response: PageableResponseType) => {
          this.pageableReviews = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getReview: async function (reviewId: number) {
      await ReviewApiService.getReview(reviewId)
        .then((response: PageableResponseType) => {
          this.currReview = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
