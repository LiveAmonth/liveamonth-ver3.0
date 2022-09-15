import { defineStore } from "pinia";
import ScheduleApiService from "@/services/ScheduleApiService";
import type { SortType } from "@/modules/types/common/SortType";
import type { EnumType } from "@/modules/types/common/EnumType";
import MemberApiService from "@/services/MemberApiService";

export const useTypeStore = defineStore("type", {
  state: () => ({
    genderType: [] as EnumType[],
    scheduleSearchType: [] as EnumType[],
    scheduleFilterType: [] as EnumType[],
    scheduleSortType: [] as SortType[],
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
  },
  persist: {
    storage: localStorage,
  },
});
