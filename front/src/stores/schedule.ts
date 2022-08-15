import { defineStore } from "pinia";
import type {
  ScheduleSearchType,
  ScheduleCardType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import type { SortType } from "@/modules/types/common/SortType";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    sortTypes: {} as SortType[],
    searchTypes: {} as EnumType[],
    filterTypes: {} as EnumType[],
    pageableSchedules: {} as PageableResponseType,
  }),
  getters: {
    scheduleDetails: (state): ScheduleCardType[] =>
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

    async getOtherSchedules(
      request: ScheduleSearchType,
      pageable: PageableRequestType
    ) {
      await ScheduleApiService.getOtherSchedules(request, pageable)
        .then((response: PageableResponseType) => {
          this.pageableSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
