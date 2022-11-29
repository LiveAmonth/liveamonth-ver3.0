import { computed, ref } from "vue";
import { useScheduleStore } from "@/stores/schedule/schedule";
import { useScheduleContentStore } from "@/stores/schedule/scheduleContent";
import type {
  ScheduleCardType,
  ScheduleContentEditor,
  ScheduleEditor,
} from "@/modules/types/schedule/ScheduleTypes";
import type { PageableRequestType } from "@/modules/types/pagination/PaginationTypes";
import { DomainType } from "@/modules/enums/constants";

export const useSchedule = () => {
  const type: string = DomainType.SCHEDULE;
  const store = useScheduleStore();
  const contentStore = useScheduleContentStore();

  const error = ref();
  const isPending = ref<boolean>(false);

  const request = computed(() => store.searchCond);
  const otherSchedules = computed(() => store.otherScheduleCards);
  const bestSchedules = computed(() => store.bestSchedules);
  const followedSchedules = computed(() => store.followedSchedules);
  const currentSchedule = computed(() => store.currentSchedule);
  const schedulePage = computed(() => store.schedulePage);
  const currScheduleContents = computed(() => contentStore.scheduleContents);

  // my page schedules
  const mySchedules = computed(() => store.mySchedules);

  // 내 스케줄 관리
  const editableSchedules = computed(() => store.editableSchedules);
  const editedSchedule = computed(() => store.editedSchedule);

  const contentCollapse = computed(() => contentStore.contentCollapse);

  const hasCurrentSchedule = computed(() => store.hasCurrentSchedule);
  const hasEditedSchedule = computed(() => store.hasEditedSchedule);
  const hasMySchedules = computed(() => store.hasMySchedules);
  const hasFollowedSchedules = computed(() => store.hasFollowedSchedules);
  const hasOtherSchedules = computed(() => store.hasOtherSchedules);
  const hasBestSchedules = computed(() => store.hasBestSchedules);

  // Schedule Global
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

  // Other Schedule View
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

  const getBestSchedules = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getBestSchedules();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getOtherScheduleCard = (id: number) => {
    return otherSchedules.value.find((value) => value.id === id);
  };

  // My Schedule View
  const getEditableSchedules = async (loginId: string) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getEditableSchedules(loginId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getInfiniteSchedules = async (
    loginId: string,
    size: number,
    lastId: number | null,
    isMyPage = false
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      isMyPage
        ? await store.getMySchedules(loginId, size, lastId)
        : await store.getFollowedSchedules(loginId, size, lastId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const addSchedule = async (loginId: string, form: ScheduleEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.addSchedule(loginId, form);
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

  const setEditedSchedule = async (selectedId: number) => {
    error.value = null;
    try {
      await store.setEditedSchedule(
        editableSchedules.value.find(
          (value) => value.id == selectedId
        ) as ScheduleCardType
      );
    } catch (err) {
      error.value = err;
    }
  };

  const setCurrentSchedule = async (isMain: boolean, selectedId: number) => {
    error.value = null;
    try {
      const selectedSchedule = isMain
        ? (followedSchedules.value.find(
            (value) => value.id == selectedId
          ) as ScheduleCardType)
        : (otherSchedules.value.find(
            (value) => value.id == selectedId
          ) as ScheduleCardType);
      await store.setCurrentSchedule(selectedSchedule);
      await store.viewCountUp(selectedId);
    } catch (err) {
      error.value = err;
    }
  };

  const handleContentCollapse = async (isOpen: boolean) => {
    isOpen
      ? contentStore.openContentCollapse()
      : contentStore.closeContentCollapse();
  };

  const getInitialSelectedId = () => {
    if (!editableSchedules.value.length) return "";
    return editableSchedules.value.includes(editedSchedule.value)
      ? editedSchedule.value.id
      : editableSchedules.value[0].id;
  };

  return {
    error,
    isPending,
    type,
    request,
    schedulePage,
    otherSchedules,
    bestSchedules,
    currentSchedule,
    currScheduleContents,
    contentCollapse,
    mySchedules,
    followedSchedules,
    editedSchedule,
    editableSchedules,
    hasCurrentSchedule,
    hasEditedSchedule,
    hasMySchedules,
    hasFollowedSchedules,
    hasOtherSchedules,
    hasBestSchedules,
    getOtherScheduleCard,
    getOtherSchedules,
    getScheduleContents,
    getEditableSchedules,
    getInfiniteSchedules,
    getBestSchedules,
    addSchedule,
    editSchedule,
    deleteSchedule,
    addContent,
    editContent,
    deleteContent,
    setEditedSchedule,
    setCurrentSchedule,
    handleContentCollapse,
    getInitialSelectedId,
  };
};
