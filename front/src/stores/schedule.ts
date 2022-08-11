import { defineStore } from "pinia";
import type {
  ScheduleContentType,
  ScheduleSearchType,
  ScheduleCardType,
  ScheduleDetailType,
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
    searchCond: {} as EnumType[],
    pageableSchedules: {} as PageableResponseType,
  }),
  getters: {
    scheduleDetails: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
  },
  actions: {
    async getSortTypes() {
      await ScheduleApiService.getSortTypes()
        .then((response: SortType[]) => {
          this.sortTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async getScheduleSearchCond() {
      await ScheduleApiService.getScheduleSearchCond()
        .then((response: EnumType[]) => {
          this.searchCond = response;
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
          console.log(response.content);
          this.pageableSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
