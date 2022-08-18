import { defineStore } from "pinia";
import ScheduleContentDetail from "@/modules/class/ScheduleContentDetail";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import { ref } from "vue";
import type {
  CalendarExtendedType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";

export const useScheduleContentStore = defineStore("scheduleContent", {
  state: () => ({
    contentForm: new ScheduleContentDetail() as ScheduleContentFormType,
    scheduleContents: [] as ScheduleContentType[],
  }),
  getters: {},
  actions: {
    async setContent(event: any) {
      await this.contentForm.setAttr(event);
    },
    async resetContent() {
      await this.contentForm.resetAttr();
    },

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
  },
});
