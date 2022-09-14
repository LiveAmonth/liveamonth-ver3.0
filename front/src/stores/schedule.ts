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
import type ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    sortTypes: [] as SortType[],
    searchTypes: [] as EnumType[],
    filterTypes: [] as EnumType[],
    searchCond: new ScheduleSearchCond() as ScheduleSearchType,
    pageableSchedules: {} as PageableResponseType,
    mySchedules: [] as ScheduleCardType[],
    currSchedule: {} as ScheduleCardType,
  }),
  getters: {
    otherScheduleCards: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
  },
  actions: {
    getSearchTypes: async function () {
      await ScheduleApiService.getSearchTypes()
        .then((response: EnumType[]) => {
          this.searchTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getFilterTypes: async function () {
      await ScheduleApiService.getFilterTypes()
        .then((response: EnumType[]) => {
          this.filterTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getSortTypes: async function () {
      await ScheduleApiService.getSortTypes()
        .then((response: SortType[]) => {
          this.sortTypes = response;
        })
        .catch((error) => {
          throw error;
        });
    },

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
        .then((response: ScheduleCardType[]) => {
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
        this.currSchedule = data;
      }
    },
  },
  persist: {
    paths: [
      "sortTypes",
      "searchTypes",
      "filterTypes",
      "searchCond",
      "pageableSchedules",
      "currSchedule",
    ],
    storage: sessionStorage,
  },
});
