import { computed, onMounted, ref } from "vue";
import type { ScheduleContentType } from "@/modules/types/schedule/ScheduleType";
import CalendarService from "@/services/CalendarService";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type { ScheduleContentFormType } from "@/modules/types/form/FormType";

export const useCalendarEvent = () => {
  const getEvents = ref([]);
  const contentStore = useScheduleContentStore();
  const contentCollapse = ref<number>();

  const scheduleContentDetail = computed(
    (): ScheduleContentFormType => contentStore.contentForm
  );

  const setContentCollapse = () => {
    contentCollapse.value = contentStore.scheduleContents[0].id;
  };

  const setContent = async (state: boolean, event: any) => {
    state
      ? await contentStore.setContent(event)
      : (contentCollapse.value = event.id);
  };

  const resetContent = async () => {
    await contentStore.resetContent();
  };

  const setEvents = async () => {
    getEvents.value = await contentStore.setScheduleEvent();
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

  onMounted(setEvents);

  return {
    scheduleContentDetail,
    getEvents,
    contentCollapse,
    setContent,
    setContentCollapse,
    resetContent,
    setEvents,
    createEvents,
    // updateEvents,
    // deleteEvents,
  };
};
