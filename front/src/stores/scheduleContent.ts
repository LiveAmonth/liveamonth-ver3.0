import { defineStore } from "pinia";
import { ref } from "vue";
import type {
  CalendarExtendedType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";

const collapseArr: number[] = [];
export const useScheduleContentStore = defineStore("scheduleContent", {
  state: () => ({
    scheduleContents: [] as ScheduleContentType[],
    contentCollapse: collapseArr,
  }),
  getters: {},
  actions: {
    async getScheduleContents(id: number) {
      await ScheduleApiService.getScheduleContents(id)
        .then((response: ScheduleContentType[]) => {
          this.scheduleContents = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    async setScheduleEvent() {
      const data: any = ref([]);
      this.scheduleContents.forEach((value: ScheduleContentType) => {
        data.value.push({
          id: value.id,
          title: value.title,
          start: value.timePeriod.startDateTime,
          end: value.timePeriod.endDateTime,
          extendedProps: <CalendarExtendedType>{
            cost: value.cost,
            content: value.content,
          },
          allDay: false,
        });
      });
      return data;
    },

    async setContentCollapse(id: number) {
      this.contentCollapse.push(id);
    },
  },
});
