import { defineStore } from "pinia";
import { ref } from "vue";
import type {
  CalendarExtendedType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";
import { useDate } from "@/composables/date";
import type { EventApi } from "@fullcalendar/common";

const collapseArr: number[] = [];
const { convertDateTime } = useDate();
export const useScheduleContentStore = defineStore("scheduleContent", {
  state: () => ({
    selectContent: {} as ScheduleContentType,
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

    async setScheduleEvents() {
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

    setContent(event: EventApi) {
      this.selectContent = {
        id: Number(event.id),
        title: event.title,
        content: event.extendedProps.content,
        cost: event.extendedProps.cost,
        timePeriod: {
          startDateTime: convertDateTime(event.start),
          endDateTime: convertDateTime(event.end),
        },
      };
    },

    resetContent() {
      this.selectContent = {
        id: 0,
        title: "",
        content: "",
        cost: 0,
        timePeriod: {
          startDateTime: "",
          endDateTime: "",
        },
      };
    },

    async setContentCollapse(id: number) {
      this.contentCollapse.push(id);
    },
  },
});
