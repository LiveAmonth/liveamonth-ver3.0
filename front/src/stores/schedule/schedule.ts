import ScheduleApiService from "@/services/schdule/ScheduleApiService";
import { defineStore } from "pinia";
import type {
  ScheduleCardType,
  ScheduleEditor,
} from "@/modules/types/schedule/ScheduleTypes";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/common/PageableType";
import { ScheduleSearchCond } from "@/modules/types/schedule/ScheduleTypes";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    searchCond: new ScheduleSearchCond(),
    pageableSchedules: {} as PageableResponseType,
    mySchedules: [] as ScheduleCardType[],
    followedSchedules: [] as ScheduleCardType[],
    editedSchedule: {} as ScheduleCardType,
    currentSchedule: {} as ScheduleCardType,
  }),
  getters: {
    otherScheduleCards: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
    schedulePage: (state): PageableType => state.pageableSchedules.pageable,
    hasCurrentSchedule: (state): boolean => !!state.currentSchedule.id,
    hasEditedSchedule: (state): boolean => !!state.editedSchedule.id,
  },
  actions: {
    getOtherSchedules: async function (pageable: PageableRequestType) {
      await ScheduleApiService.getOtherSchedules(this.searchCond, pageable)
        .then((response: PageableResponseType) => {
          this.pageableSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getMySchedules: async function (loginId: string, size = null) {
      await ScheduleApiService.getMySchedules(loginId, size)
        .then((response: ScheduleCardType[]) => {
          this.mySchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getInfiniteSchedules: async function (
      loginId: string,
      size: number,
      lastId: number | null
    ) {
      await ScheduleApiService.getMySchedules(loginId, size, lastId)
        .then((response: ScheduleCardType[]) => {
          lastId
            ? response.forEach((value) => this.mySchedules.push(value))
            : (this.mySchedules = response);
        })
        .catch((error) => {
          throw error;
        });
    },

    getAdditionalFollowedSchedules: async function (
      loginId: string,
      size: number,
      lastId: number | null
    ) {
      await ScheduleApiService.getFollowedSchedules(loginId, size, lastId)
        .then((response: ScheduleCardType[]) => {
          lastId
            ? response.forEach((value) => this.followedSchedules.push(value))
            : (this.followedSchedules = response);
        })
        .catch((error) => {
          throw error;
        });
    },

    addSchedule: async function (form: ScheduleEditor) {
      await ScheduleApiService.addSchedule(form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editSchedule: async function (scheduleId: number, form: ScheduleEditor) {
      await ScheduleApiService.editSchedule(scheduleId, form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    deleteSchedule: async function (scheduleId: number) {
      await ScheduleApiService.deleteSchedule(scheduleId)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    setEditedSchedule: async function (data: ScheduleCardType) {
      this.editedSchedule = data;
    },

    setCurrentSchedule: async function (data: ScheduleCardType) {
      this.currentSchedule = data;
    },
  },
  persist: {
    storage: sessionStorage,
    paths: ["editedSchedule", "currentSchedule"],
  },
});
