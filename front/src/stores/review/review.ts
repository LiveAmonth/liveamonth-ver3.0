import { defineStore } from "pinia";
import {
  ReviewEditor,
  ReviewSearchCond,
} from "@/modules/types/review/ReviewTypes";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/pagination/PaginationTypes";
import ReviewApiService from "@/services/review/ReviewApiService";
import type {
  ReviewListType,
  ReviewDetailType,
  TagType,
} from "@/modules/types/review/ReviewTypes";
import type { IdResponseType } from "@/modules/types/common/CommonTypes";

export const useReviewStore = defineStore("review", {
  state: () => ({
    searchCond: new ReviewSearchCond(),
    pageableReviews: {} as PageableResponseType,
    bestReviews: {} as ReviewListType[],
    currReview: {} as ReviewDetailType,
    myReviews: [] as ReviewListType[],
    addedReviewId: 0,
    recommendationTags: [] as TagType[],
  }),
  getters: {
    otherReviews: (state): ReviewListType[] =>
      state.pageableReviews.content as ReviewListType[],
    reviewPage: (state): PageableType => state.pageableReviews.pageable,
    hasMyReviews: (state): boolean => !!state.myReviews.length,
    hasOtherReviews: (state): boolean => !!state.pageableReviews,
    hasBestReviews: (state): boolean => !!state.bestReviews.length,
  },
  actions: {
    addReview: async function (loginId: string, form: ReviewEditor) {
      await ReviewApiService.addReview(loginId, form)
        .then((response: IdResponseType) => {
          this.addedReviewId = response.id;
        })
        .catch((error) => {
          throw error;
        });
    },

    editReview: async function (reviewId: number, form: ReviewEditor) {
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

    getBestReviews: async function () {
      await ReviewApiService.getBestReviews()
        .then((response: ReviewListType[]) => {
          this.bestReviews = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getMyReviews: async function (
      loginId: string,
      size: number,
      lastId: number | null
    ) {
      await ReviewApiService.getMyReviews(loginId, size, lastId)
        .then((response: ReviewListType[]) => {
          lastId
            ? response.forEach((value) => this.myReviews.push(value))
            : (this.myReviews = response);
        })
        .catch((error) => {
          throw error;
        });
    },

    getReview: async function (reviewId: number) {
      await ReviewApiService.getReview(reviewId)
        .then((response: ReviewDetailType) => {
          this.currReview = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    viewCountUp: async function (id: number) {
      await ReviewApiService.viewCountUp(id);
    },

    getRecommendationTags: async function () {
      await ReviewApiService.getRecommendationTags()
        .then((response: TagType[]) => {
          this.recommendationTags = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
