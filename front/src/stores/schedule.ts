import { defineStore } from "pinia";
import type {
  ScheduleContentType,
  ScheduleType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";
import type { EnumType } from "@/modules/types/common/EnumType";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    scheduleSearchCond: {} as EnumType[],
    otherSchedules: {} as ScheduleType[],
  }),
  getters: {
    numOfSchedules: (state): number => state.otherSchedules.length,
    scheduleContents:
      (state) =>
      (index: number): ScheduleContentType[] =>
        state.otherSchedules[index].contents,
  },
  actions: {
    async getScheduleSearchCond() {
      await ScheduleApiService.getScheduleSearchCond()
        .then((response: EnumType[]) => {
          this.scheduleSearchCond = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    async getOtherSchedules(size: number) {
      await ScheduleApiService.getOtherSchedules(size)
        .then((response: ScheduleType[]) => {
          this.otherSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
