import { computed } from "vue";
import { useScheduleContentStore } from "@/stores/schedule/scheduleContent";
import { useDate } from "@/composables/common/date";
import type { EventApi } from "@fullcalendar/common";
import type {
  ScheduleContentEditor,
  ScheduleContentType,
} from "@/modules/types/schedule/ScheduleTypes";

export const useCalendarEvent = () => {
  const store = useScheduleContentStore();
  const contentCollapse = computed((): number[] => store.contentCollapse);
  const selectedContent = computed(
    (): ScheduleContentType => store.selectContent
  );
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

  const updateEvent = async (event: EventApi) => {
    const request = {
      title: event.title,
      content: event.extendedProps.content,
      cost: event.extendedProps.cost,
      timePeriod: {
        startDateTime: getDateTime(event.start),
        endDateTime: getDateTime(event.end),
      },
    } as ScheduleContentEditor;
    await store.editContent(Number(event.id), request);
  };

  return {
    contentCollapse,
    selectedContent,
    setContent,
    resetContent,
    setContentCollapse,
    getEvents,
    updateEvent,
  };
};
