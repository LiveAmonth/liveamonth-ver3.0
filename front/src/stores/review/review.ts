import { defineStore } from "pinia";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/common/PageableType";
import ReviewApiService from "@/services/review/ReviewApiService";

export const useReviewStore = defineStore("review", {
  state: () => ({
    pageableReviews: {} as PageableResponseType,
    currReview: {} as object,
  }),
  getters: {
    otherScheduleCards: (state) => state.pageableReviews.content,
    schedulePage: (state): PageableType => state.pageableReviews.pageable,
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
      await ReviewApiService.getReviews(null, pageable)
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
