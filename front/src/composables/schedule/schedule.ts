import { computed, ref } from "vue";
import { useMember } from "@/composables/member/member";
import { useScheduleStore } from "@/stores/schedule";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleSearchType,
} from "@/modules/types/schedule/ScheduleType";
import type {
  PageableRequestType,
  PageableType,
} from "@/modules/types/common/PageableType";
import type ScheduleEditor from "@/modules/class/schedule/ScheduleEditor";
import type ScheduleContentEditor from "@/modules/class/schedule/ScheduleContentEditor";
import type { MyScheduleCardType } from "@/modules/types/schedule/ScheduleType";

export const useSchedule = () => {
  const store = useScheduleStore();
  const contentStore = useScheduleContentStore();
  const { simpleProfile } = useMember();
  const error = ref();
  const isPending = ref<boolean>(false);
  const type = "schedule";

  const request = computed((): ScheduleSearchType => store.searchCond);
  const otherSchedules = computed(
    (): ScheduleCardType[] => store.otherScheduleCards
  );
  const schedulePage = computed((): PageableType => store.schedulePage);
  const editedSchedule = computed(
    (): MyScheduleCardType => store.editedSchedule
  );
  const currScheduleContents = computed(
    (): ScheduleContentType[] => contentStore.scheduleContents
  );
  const mySchedules = computed((): MyScheduleCardType[] => store.mySchedules);
  const followedSchedules = computed(
    (): ScheduleCardType[] => store.followedSchedules
  );

  const infiniteSchedules = (isMyPage: boolean) => {
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

  const getOtherSchedule = (id: number): ScheduleCardType => {
    return otherSchedules.value.find(
      (value: ScheduleCardType) => value.id === id
    ) as ScheduleCardType;
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

  const setSchedule = async (selectedId: number) => {
    await store.setSchedule(selectedId);
  };

  const isScheduleEmpty = () => {
    return !mySchedules.value.length;
  };

  const isMemberEq = () => {
    return editedSchedule.value.memberId === simpleProfile.value.id;
  };

  const getInitialSelectedId = () => {
    if (isScheduleEmpty()) return "";
    if (!editedSchedule.value.id) return mySchedules.value[0].id;
    return isMemberEq() ? editedSchedule.value.id : mySchedules.value[0].id;
  };

  return {
    error,
    isPending,
    type,
    request,
    schedulePage,
    otherSchedules,
    currScheduleContents,
    mySchedules,
    followedSchedules,
    infiniteSchedules,
    editedSchedule,
    getOtherSchedule,
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
    setSchedule,
    getInitialSelectedId,
  };
};
