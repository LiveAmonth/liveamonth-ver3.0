import ScheduleSearchCond from "@/modules/class/schedule/ScheduleCond";
import ScheduleApiService from "@/services/ScheduleApiService";
import { defineStore } from "pinia";
import type {
  ScheduleCardType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import type { SortType } from "@/modules/types/common/SortType";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    sortTypes: [] as SortType[],
    searchTypes: [] as EnumType[],
    filterTypes: [] as EnumType[],
    searchCond: new ScheduleSearchCond() as ScheduleSearchType,
    pageableSchedules: {} as PageableResponseType,
  }),
  getters: {
    otherScheduleCards: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
  },
  actions: {
    async getSearchTypes() {
      await ScheduleApiService.getSearchTypes()
        .then((response: EnumType[]) => {
          this.searchTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getFilterTypes() {
      await ScheduleApiService.getFilterTypes()
        .then((response: EnumType[]) => {
          this.filterTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getSortTypes() {
      await ScheduleApiService.getSortTypes()
        .then((response: SortType[]) => {
          this.sortTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getOtherSchedules(pageable: PageableRequestType) {
      await ScheduleApiService.getOtherSchedules(this.searchCond, pageable)
        .then((response: PageableResponseType) => {
          this.pageableSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
  persist: {
    // paths: ["sortTypes", "searchTypes", "filterTypes", "searchCond"],
    storage: sessionStorage,
  },
});
