import ScheduleSearchCond from "@/modules/class/schedule/ScheduleCond";
import ScheduleApiService from "@/services/ScheduleApiService";
import { defineStore } from "pinia";
import type {
  MyScheduleCardType,
  ScheduleCardType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/common/PageableType";
import type ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    searchCond: new ScheduleSearchCond() as ScheduleSearchType,
    pageableSchedules: {} as PageableResponseType,
    mySchedules: [] as MyScheduleCardType[],
    editedSchedule: {} as MyScheduleCardType,
  }),
  getters: {
    otherScheduleCards: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
    schedulePage: (state): PageableType => state.pageableSchedules.pageable,
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

    getMySchedules: async function (loginId: string) {
      await ScheduleApiService.getMySchedules(loginId)
        .then((response: MyScheduleCardType[]) => {
          this.mySchedules = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    addSchedule: async function (memberId: number, form: ScheduleEditor) {
      await ScheduleApiService.addSchedule(memberId, form)
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

    setSchedule: async function (selectedId: number) {
      const data = this.mySchedules.find((value) => value.id === selectedId);
      if (data) {
        this.editedSchedule = data;
      }
    },
  },
  persist: {
    paths: ["pageableSchedules"],
    storage: sessionStorage,
  },
});
