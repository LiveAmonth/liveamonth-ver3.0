import { useScheduleStore } from "@/stores/schedule";
import { computed, ref } from "vue";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { SortType } from "@/modules/types/common/SortType";
import type ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";
import type ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";

export const useSchedule = () => {
  const store = useScheduleStore();
  const contentStore = useScheduleContentStore();

  const error = ref();
  const isPending = ref<boolean>(false);
  const type = "schedule";

  const searchTypes = computed((): EnumType[] => store.searchTypes);
  const sortTypes = computed((): SortType[] => store.sortTypes);
  const filterTypes = computed((): EnumType[] => store.filterTypes);

  const request = computed((): ScheduleSearchType => store.searchCond);
  const otherSchedules = computed(
    (): ScheduleCardType[] => store.otherScheduleCards
  );
  const currScheduleContents = computed(
    (): ScheduleContentType[] => contentStore.scheduleContents
  );
  const mySchedules = computed((): ScheduleCardType[] => store.mySchedules);

  const getSearchTypes = async () => {
    error.value = null;
    try {
      await store.getSearchTypes();
      error.value = null;
    } catch (err) {
      error.value = err;
    }
  };

  const getFilterTypes = async () => {
    error.value = null;
    try {
      await store.getFilterTypes();
      error.value = null;
    } catch (err) {
      error.value = err;
    }
  };

  const getSortTypes = async () => {
    error.value = null;
    try {
      await store.getSortTypes();
      error.value = null;
    } catch (err) {
      error.value = err;
    }
  };

  const getOtherSchedules = async (pageable: PageableRequestType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getOtherSchedules(pageable);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getOtherSchedule = (id: number): ScheduleCardType => {
    return otherSchedules.value.find(
      (value: ScheduleCardType) => value.id === id
    ) as ScheduleCardType;
  };

  const getScheduleContents = async (id: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await contentStore.getScheduleContents(id);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getMySchedules = async (loginId: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getMySchedules(loginId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const addSchedule = async (memberId: number, form: ScheduleEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.addSchedule(memberId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editSchedule = async (scheduleId: number, form: ScheduleEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.editSchedule(scheduleId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const deleteSchedule = async (scheduleId: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.deleteSchedule(scheduleId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const addContent = async (
    scheduleId: number,
    form: ScheduleContentEditor
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      await contentStore.addContent(scheduleId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editContent = async (
    contentId: number,
    form: ScheduleContentEditor
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      await contentStore.editContent(contentId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const deleteContent = async (contentId: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await contentStore.deleteContent(contentId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  return {
    error,
    isPending,
    type,
    searchTypes,
    sortTypes,
    filterTypes,
    request,
    otherSchedules,
    currScheduleContents,
    mySchedules,
    getSortTypes,
    getSearchTypes,
    getFilterTypes,
    getOtherSchedule,
    getOtherSchedules,
    getScheduleContents,
    getMySchedules,
    addSchedule,
    editSchedule,
    deleteSchedule,
    addContent,
    editContent,
    deleteContent,
  };
};
