import { useTypeStore } from "@/stores/type";
import { computed } from "vue";

export const useType = () => {
  const store = useTypeStore();

  const genderType = computed(() => store.genderType);
  const scheduleSearchType = computed(() => store.scheduleSearchType);
  const scheduleFilterType = computed(() => store.scheduleFilterType);
  const scheduleSortType = computed(() => store.scheduleSortType);

  const getGenderType = async () => {
    await store.getGenderType();
  };

  const getScheduleSearchType = async () => {
    await store.getScheduleSearchType();
  };

  const getScheduleFilterType = async () => {
    await store.getScheduleFilterType();
  };

  const getScheduleSortType = async () => {
    await store.getScheduleSortType();
  };

  return {
    genderType,
    scheduleSearchType,
    scheduleFilterType,
    scheduleSortType,
    getGenderType,
    getScheduleSearchType,
    getScheduleFilterType,
    getScheduleSortType,
  };
};
