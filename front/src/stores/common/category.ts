import ScheduleApiService from "@/services/schdule/ScheduleApiService";
import MemberApiService from "@/services/member/MemberApiService";
import { defineStore } from "pinia";
import type { SortType } from "@/modules/types/common/SortType";
import type { EnumType } from "@/modules/types/common/EnumType";
import ReviewApiService from "@/services/review/ReviewApiService";

export const useCategoryStore = defineStore("category", {
  state: () => ({
    genderType: [] as EnumType[],
    scheduleSearchType: [] as EnumType[],
    scheduleFilterType: [] as EnumType[],
    scheduleSortType: [] as SortType[],
    reviewCategory: [] as EnumType[],
    reviewSearchType: [] as EnumType[],
    reviewSortType: [] as SortType[],
  }),
  actions: {
    getGenderType: async function () {
      await MemberApiService.getGenderTypes()
        .then((response: EnumType[]) => {
          this.genderType = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getScheduleSearchType: async function () {
      await ScheduleApiService.getSearchTypes()
        .then((response: EnumType[]) => {
          this.scheduleSearchType = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getScheduleFilterType: async function () {
      await ScheduleApiService.getFilterTypes()
        .then((response: EnumType[]) => {
          this.scheduleFilterType = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getScheduleSortType: async function () {
      await ScheduleApiService.getSortTypes()
        .then((response: SortType[]) => {
          this.scheduleSortType = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getReviewCategory: async function () {
      await ReviewApiService.getReviewCategory()
        .then((response: EnumType[]) => {
          this.reviewCategory = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getReviewSearchType: async function () {
      await ReviewApiService.getSearchTypes()
        .then((response: EnumType[]) => {
          this.reviewSearchType = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getReviewSortType: async function () {
      await ReviewApiService.getSortTypes()
        .then((response: SortType[]) => {
          this.reviewSortType = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
  persist: {
    storage: localStorage,
  },
});
