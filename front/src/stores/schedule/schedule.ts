import ScheduleApiService from "@/services/schdule/ScheduleApiService";
import { defineStore } from "pinia";
import type {
  EditableScheduleType,
  MyScheduleType,
  ScheduleCardType,
  ScheduleEditor,
} from "@/modules/types/schedule/ScheduleTypes";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/pagination/PaginationTypes";
import { ScheduleSearchCond } from "@/modules/types/schedule/ScheduleTypes";
import scheduleApiService from "@/services/schdule/ScheduleApiService";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    searchCond: new ScheduleSearchCond(),
    pageableSchedules: {} as PageableResponseType,
    mySchedules: [] as MyScheduleType[],
    editableSchedules: [] as EditableScheduleType[],
    followedSchedules: [] as ScheduleCardType[],
    editedSchedule: {} as EditableScheduleType,
    currentSchedule: {} as ScheduleCardType,
  }),
  getters: {
    otherScheduleCards: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
    schedulePage: (state): PageableType => state.pageableSchedules.pageable,
    hasCurrentSchedule: (state): boolean => !!state.currentSchedule.id,
    hasEditedSchedule: (state): boolean => !!state.editedSchedule.id,
    hasMySchedules: (state): boolean => !!state.mySchedules.length,
    hasFollowedSchedules: (state): boolean => !!state.followedSchedules.length,
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

    getPopularSchedules: async function (pageable: PageableRequestType) {
      await ScheduleApiService.getOtherSchedules(
        new ScheduleSearchCond(),
        pageable
      )
        .then((response: PageableResponseType) => {
          this.pageableSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getEditableSchedules: async function (loginId: string) {
      await ScheduleApiService.getEditableSchedules(loginId)
        .then((response: EditableScheduleType[]) => {
          this.editableSchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getMySchedules: async function (
      loginId: string,
      size: number,
      lastId: number | null
    ) {
      await ScheduleApiService.getMySchedules(loginId, size, lastId)
        .then((response: MyScheduleType[]) => {
          lastId
            ? response.forEach((value) => this.mySchedules.push(value))
            : (this.mySchedules = response);
        })
        .catch((error) => {
          throw error;
        });
    },

    getFollowedSchedules: async function (
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

    addSchedule: async function (loginId: string, form: ScheduleEditor) {
      await ScheduleApiService.addSchedule(loginId, form)
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

    setEditedSchedule: async function (data: EditableScheduleType) {
      this.editedSchedule = data;
    },

    setCurrentSchedule: async function (data: ScheduleCardType) {
      this.currentSchedule = data;
    },

    viewCountUp: async function (id: number) {
      await scheduleApiService.viewCountUp(id);
    },
  },
  persist: {
    storage: sessionStorage,
    paths: ["editedSchedule", "currentSchedule"],
  },
});
