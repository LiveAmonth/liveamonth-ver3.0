import ScheduleApiService from "@/services/schdule/ScheduleApiService";
import MemberApiService from "@/services/member/MemberApiService";
import ReviewApiService from "@/services/review/ReviewApiService";
import CityApiService from "@/services/city/CityApiService";
import { defineStore } from "pinia";
import type { SortType } from "@/modules/types/common/SearchEngineTypes";
import type { EnumType } from "@/modules/types/common/CommonTypes";

export const useCategoryStore = defineStore("category", {
  state: () => ({
    cityNames: [] as EnumType[],
    genderType: [] as EnumType[],
    scheduleSearchType: [] as EnumType[],
    scheduleFilterType: [] as EnumType[],
    scheduleSortType: [] as SortType[],
    reviewCategory: [] as EnumType[],
    reviewMenuGroup: [] as EnumType[],
    reviewSearchType: [] as EnumType[],
    reviewSortType: [] as SortType[],
  }),
  getters: {
    hasCityNames: (state): boolean => !!state.cityNames.length,
    hasGenderType: (state): boolean => !!state.genderType.length,
    hasScheduleCategory: (state): boolean =>
      !!state.scheduleSearchType.length &&
      !!state.scheduleFilterType.length &&
      !!state.scheduleSortType.length,
    hasReviewCategory: (state): boolean =>
      !!state.reviewCategory.length &&
      !!state.reviewMenuGroup.length &&
      !!state.reviewSearchType.length &&
      !!state.reviewSortType.length,
  },
  actions: {
    getCityNames: async function () {
      await CityApiService.getCityNames()
        .then((response: EnumType[]) => {
          this.cityNames = response;
        })
        .catch((error) => {
          throw error;
        });
    },

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

    getReviewMenuGroup: async function () {
      await ReviewApiService.getReviewMenuGroup()
        .then((response: EnumType[]) => {
          this.reviewMenuGroup = response;
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
