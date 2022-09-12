import CalendarService from "@/services/CalendarService";
import { computed } from "vue";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type { EventApi } from "@fullcalendar/common";
import { useDate } from "@/composables/date";
import ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";

export const useCalendarEvent = () => {
  const store = useScheduleContentStore();
  const contentCollapse = computed(() => store.contentCollapse);
  const scheduleContents = computed(() => store.scheduleContents);
  const selectedContent = computed(() => store.selectContent);
  const { getDateTime } = useDate();
  const setContentCollapse = async (id = store.scheduleContents[0]?.id) => {
    await store.setContentCollapse(id);
  };
  const setContent = (event: EventApi) => {
    store.setContent(event);
  };
  const resetContent = () => {
    store.resetContent();
  };
  const getEvents = () => {
    return store.getScheduleEvents();
  };

  const createEvents = async (event: EventApi) => {
    const request = {
      title: event.title,
      content: event.extendedProps.content,
      cost: event.extendedProps.cost,
      timePeriod: {
        startDateTime: getDateTime(event.start),
        endDateTime: getDateTime(event.end),
      },
    };
    await CalendarService.storeEvent(request);
  };

  const updateEvents = async (
    contentId: number,
    form: ScheduleContentEditor
  ) => {
  };

  const deleteEvents = async () => {};

  return {
    scheduleContents,
    contentCollapse,
    selectedContent,
    setContent,
    resetContent,
    setContentCollapse,
    getEvents,
    createEvents,
    updateEvents,
    // deleteEvents,
  };
};
