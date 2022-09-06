import { computed, onMounted, ref } from "vue";
import type { ScheduleContentType } from "@/modules/types/schedule/ScheduleType";
import CalendarService from "@/services/CalendarService";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";

export const useCalendarEvent = () => {
  const store = useScheduleContentStore();
  const contentCollapse = computed(() => store.contentCollapse);

  const scheduleContents = computed(() => store.scheduleContents);
  const setContentCollapse = async (id = store.scheduleContents[0]?.id) => {
    await store.setContentCollapse(id);
  };

  const setContent = async (event: any) => {
    await store.setContent(event);
  };

  const setEvents = async () => {
    return await store.setScheduleEvents();
  };

  const createEvents = async (event: any) => {
    const request: ScheduleContentType = {
      id: event.id,
      title: event.title,
      timePeriod: {
        startDateTime: event.start.toString(),
        endDateTime: event.end.toString(),
      },
      content: event.extendedProps.content,
      cost: event.extendedProps.cost,
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
    setContent,
    setContentCollapse,
    setEvents,
    createEvents,
    // updateEvents,
    // deleteEvents,
  };
};
