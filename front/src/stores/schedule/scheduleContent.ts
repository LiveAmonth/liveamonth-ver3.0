import { defineStore } from "pinia";
import { ref } from "vue";
import type {
  CalendarExtendedType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import ScheduleApiService from "@/services/ScheduleApiService";
import { useDate } from "@/composables/common/date";
import type { EventApi } from "@fullcalendar/common";
import type ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";
import scheduleApiService from "@/services/ScheduleApiService";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";

const collapseArr: number[] = [];
const { getDateTime } = useDate();
export const useScheduleContentStore = defineStore("scheduleContent", {
  state: () => ({
    selectContent: {} as ScheduleContentType,
    scheduleContents: [] as ScheduleContentType[],
    contentCollapse: collapseArr,
  }),
  getters: {},
  actions: {
    addContent: async function (
      scheduleId: number,
      form: ScheduleContentEditor
    ) {
      await ScheduleApiService.addScheduleContent(scheduleId, form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editContent: async function (
      contentId: number,
      form: ScheduleContentFormType
    ) {
      await scheduleApiService
        .editScheduleContent(contentId, form)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    deleteContent: async function (contentId: number) {
      await scheduleApiService
        .deleteScheduleContent(contentId)
        .then((response: string) => {
          return response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getScheduleContents: async function (scheduleId: number) {
      await ScheduleApiService.getScheduleContents(scheduleId)
        .then((response: ScheduleContentType[]) => {
          this.scheduleContents = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    getScheduleEvents: function () {
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

    setContent: function (event: EventApi) {
      this.selectContent = {
        id: Number(event.id),
        title: event.title,
        content: event.extendedProps.content,
        cost: event.extendedProps.cost,
        timePeriod: {
          startDateTime: getDateTime(event.start),
          endDateTime: getDateTime(event.end),
        },
      };
    },

    resetContent: function () {
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

    setContentCollapse: function (id: number) {
      this.contentCollapse.push(id);
    },
  },
});
