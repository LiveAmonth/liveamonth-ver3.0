import { useScheduleStore } from "@/stores/schedule";
import { computed, ref } from "vue";
import type { ScheduleSearchType } from "@/modules/types/schedule/ScheduleType";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { ScheduleCardType } from "@/modules/types/schedule/ScheduleType";

export const useSchedule = () => {
  const store = useScheduleStore();
  const error = ref();
  const isPending = ref<boolean>(false);

  const request = computed((): ScheduleSearchType => store.searchCond);
  const schedules = computed((): ScheduleCardType[] => store.scheduleDetails);

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

  return {
    error,
    isPending,
    request,
    schedules,
    getSortTypes,
    getSearchTypes,
    getFilterTypes,
    getOtherSchedules,
  };
};
