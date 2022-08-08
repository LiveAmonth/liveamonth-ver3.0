import { defineStore } from "pinia";
import type {
  ScheduleContentType,
  ScheduleType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
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
