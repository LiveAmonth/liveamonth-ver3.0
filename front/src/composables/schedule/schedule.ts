import { computed, ref } from "vue";
import { useMember } from "@/composables/member/member";
import { useScheduleStore } from "@/stores/schedule/schedule";
import { useScheduleContentStore } from "@/stores/schedule/scheduleContent";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleContentEditor,
  ScheduleEditor,
  ScheduleSearchCond,
} from "@/modules/types/schedule/ScheduleTypes";
import type {
  PageableRequestType,
  PageableType,
} from "@/modules/types/common/PageableType";

export const useSchedule = () => {
  const store = useScheduleStore();
  const contentStore = useScheduleContentStore();
  const { simpleProfile } = useMember();
  const error = ref();
  const isPending = ref<boolean>(false);
  const type = "schedule";

  const request = computed((): ScheduleSearchCond => store.searchCond);
  const otherSchedules = computed(
    (): ScheduleCardType[] => store.otherScheduleCards
  );
  const followedSchedules = computed(
    (): ScheduleCardType[] => store.followedSchedules
  );
  const currentSchedule = computed(
    (): ScheduleCardType => store.currentSchedule
  );
  const schedulePage = computed((): PageableType => store.schedulePage);
  const currScheduleContents = computed(
    (): ScheduleContentType[] => contentStore.scheduleContents
  );
  const mySchedules = computed((): ScheduleCardType[] => store.mySchedules);
  const editedSchedule = computed((): ScheduleCardType => store.editedSchedule);
  const contentCollapse = computed(
    (): number[] => contentStore.contentCollapse
  );

  const infiniteSchedules = (isMyPage: boolean): ScheduleCardType[] => {
    return isMyPage ? mySchedules.value : followedSchedules.value;
  };

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

  const getOtherScheduleCard = (id: number) => {
    return otherSchedules.value.find((value) => value.id === id);
  };

  // My Schedule View
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

  const getInfiniteSchedules = async (
    loginId: string,
    size: number,
    lastId: number | null,
    isMyPage = false
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      if (isMyPage) {
        await store.getInfiniteSchedules(loginId, size, lastId);
      } else {
        await store.getAdditionalFollowedSchedules(loginId, size, lastId);
      }
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const addSchedule = async (form: ScheduleEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.addSchedule(form);
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
    await store.setEditedSchedule(selectedId);
  };

  const setCurrentSchedule = async (selectedId: number) => {
    await store.setCurrentSchedule(selectedId);
  };

  const isScheduleEmpty = () => {
    return !mySchedules.value.length;
  };

  const isMemberEq = () => {
    return editedSchedule.value.profile.id == simpleProfile.value.id;
  };

  const getInitialSelectedId = () => {
    if (isScheduleEmpty()) return "";
    if (!mySchedules.value.includes(editedSchedule.value))
      return mySchedules.value[0].id;
    return isMemberEq() ? editedSchedule.value.id : mySchedules.value[0].id;
  };

  return {
    error,
    isPending,
    type,
    request,
    schedulePage,
    otherSchedules,
    currentSchedule,
    currScheduleContents,
    contentCollapse,
    mySchedules,
    followedSchedules,
    editedSchedule,
    infiniteSchedules,
    getOtherScheduleCard,
    getOtherSchedules,
    getScheduleContents,
    getMySchedules,
    getInfiniteSchedules,
    addSchedule,
    editSchedule,
    deleteSchedule,
    addContent,
    editContent,
    deleteContent,
    setEditedSchedule,
    setCurrentSchedule,
    getInitialSelectedId,
  };
};
