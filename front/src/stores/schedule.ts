import { defineStore } from "pinia";
import type {
  ScheduleSearchType,
  ScheduleCardType,
  ScheduleDetailType,
  ScheduleContentType,
  CalendarType,
  CalendarExtendedType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import type { SortType } from "@/modules/types/common/SortType";
import ScheduleSearchCond from "@/modules/class/ScheduleCond";
import type { CalendarDateType } from "element-plus";
import type { EventRefiners } from "@fullcalendar/common";
import { ref } from "vue";

export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    sortTypes: {} as SortType[],
    searchTypes: {} as EnumType[],
    filterTypes: {} as EnumType[],
    searchCond: new ScheduleSearchCond() as ScheduleSearchType,
    pageableSchedules: {} as PageableResponseType,
    schedule: {} as ScheduleDetailType,
  }),
  getters: {
    scheduleDetails: (state): ScheduleCardType[] =>
      state.pageableSchedules.content as ScheduleCardType[],
    scheduleCard: (state): ScheduleCardType => state.schedule.card,
    scheduleContents: (state): ScheduleContentType[] => state.schedule.contents,
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
    async getSchedule(nickname: string, title: string) {
      await ScheduleApiService.getSchedule(nickname, title)
        .then((response: ScheduleDetailType) => {
          this.schedule = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async setScheduleEvent() {
      const data: any = ref([]);
      this.schedule.contents.forEach((value: ScheduleContentType) => {
        data.value.push({
          title: value.title,
          start: value.date,
          end: value.date,
          extendedProps: <CalendarExtendedType>{
            cost: value.cost,
            content: value.content,
          },
          allDay: true,
        });
      });
      return data;
    },
  },
  persist: true,
});
