import { useScheduleStore } from "@/stores/schedule";
import { ref } from "vue";

export const useSchedule = () => {
  const store = useScheduleStore();
  const error = ref();
  const isPending = ref<boolean>(false);

  const getOtherSchedules = async (size: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getOtherSchedules(size);
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
    getOtherSchedules,
  };
};
