import CalendarService from "@/services/CalendarService";
import { computed } from "vue";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type { EventApi } from "@fullcalendar/common";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";
import { useDate } from "@/composables/date";

export const useCalendarEvent = () => {
  const store = useScheduleContentStore();
  const contentCollapse = computed(() => store.contentCollapse);
  const scheduleContents = computed(() => store.scheduleContents);
  const selectedContent = computed(() => store.selectContent);
  const { convertDateTime } = useDate();
  const setContentCollapse = async (id = store.scheduleContents[0]?.id) => {
    await store.setContentCollapse(id);
  };
  const setContent = (event: EventApi) => {
    store.setContent(event);
  };
  const resetContent = () => {
    store.resetContent();
  };
  const setEvents = async () => {
    return await store.setScheduleEvents();
  };

  const createEvents = async (event: EventApi) => {
    const request = {
      title: event.title,
      content: event.extendedProps.content,
      cost: event.extendedProps.cost,
      timePeriod: {
        startDateTime: convertDateTime(event.start),
        endDateTime: convertDateTime(event.end),
      },
    };
    await CalendarService.storeEvent(request);
  };
  // const updateEvents = async () => {
  // };
  // const deleteEvents = async () => {
  // };

  return {
    scheduleContents,
    contentCollapse,
    selectedContent,
    setContent,
    resetContent,
    setContentCollapse,
    setEvents,
    createEvents,
    // updateEvents,
    // deleteEvents,
  };
};
