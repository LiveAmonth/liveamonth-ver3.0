import { useScheduleStore } from "@/stores/schedule";
import { ref } from "vue";
import type { ScheduleSearchType } from "@/modules/types/schedule/ScheduleType";
import type {PageableRequestType} from "@/modules/types/common/PageableType";

export const useSchedule = () => {
  const store = useScheduleStore();
  const error = ref();
  const isPending = ref<boolean>(false);
  const getSortTypes = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getSortTypes();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getScheduleSearchCond = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getScheduleSearchCond();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };
  const getOtherSchedules = async (
    request: ScheduleSearchType,
    pageable: PageableRequestType
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getOtherSchedules(request, pageable);
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
    getSortTypes,
    getScheduleSearchCond,
    getOtherSchedules,
  };
};
