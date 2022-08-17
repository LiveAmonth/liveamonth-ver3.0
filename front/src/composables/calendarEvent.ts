import { computed, onMounted, ref } from "vue";
import { useScheduleStore } from "@/stores/schedule";
import type {
  CalendarType,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleType";
import CalendarService from "@/services/CalendarService";
import type { EventRefiners } from "@fullcalendar/common";

export const useCalendarEvent = () => {
  const getEvents = ref([]);
  const store = useScheduleStore();
  const setEvents = async () => {
    getEvents.value = await store.setScheduleEvent();
  };
  const createEvents = async (event: any) => {
    const request: ScheduleContentType = {
      id: store.schedule.card.id,
      title: event.title,
      date: event.start.toString(),
      content: event.extendedProps.content,
      cost: event.extendedProps.cost,
    };
    await CalendarService.storeEvent(request);
  };
  // const updateEvents = async () => {
  // };
  // const deleteEvents = async () => {
  // };

  onMounted(setEvents);

  return {
    getEvents,
    setEvents,
    createEvents,
    // updateEvents,
    // deleteEvents,
  };
};
