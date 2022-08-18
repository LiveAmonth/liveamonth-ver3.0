import { useScheduleStore } from "@/stores/schedule";
import { computed, ref } from "vue";
import ScheduleApiService from "@/services/ScheduleApiService";
import { useScheduleContentStore } from "@/stores/scheduleContent";
import type {
  ScheduleCardType,
  ScheduleContentType,
  ScheduleSearchType,
  ScheduleSimpleCardType,
} from "@/modules/types/schedule/ScheduleType";
import type { PageableRequestType } from "@/modules/types/common/PageableType";

export const useSchedule = () => {
  const store = useScheduleStore();
  const contentStore = useScheduleContentStore();

  const error = ref();
  const isPending = ref<boolean>(false);

  const request = computed((): ScheduleSearchType => store.searchCond);

  const otherSchedules = computed(
    (): ScheduleCardType[] => store.otherScheduleCards
  );

  const currScheduleContents = computed(
    (): ScheduleContentType[] => contentStore.scheduleContents
  );

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

  const getMemberScheduleList = async (
    loginId: string
  ): Promise<ScheduleSimpleCardType[]> => {
    return await ScheduleApiService.getMemberScheduleList(loginId).then(
      (response) => {
        return response;
      }
    );
  };

  return {
    error,
    isPending,
    request,
    otherSchedules,
    currScheduleContents,
    getOtherSchedule,
    getSortTypes,
    getSearchTypes,
    getFilterTypes,
    getOtherSchedules,
    getScheduleContents,
    getMemberScheduleList,
  };
};
