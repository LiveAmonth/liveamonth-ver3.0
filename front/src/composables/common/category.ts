import { computed } from "vue";
import { useCategoryStore } from "@/stores/common/category";

export const useCategory = () => {
  const store = useCategoryStore();

  const genderType = computed(() => store.genderType);
  const scheduleSearchType = computed(() => store.scheduleSearchType);
  const scheduleFilterType = computed(() => store.scheduleFilterType);
  const scheduleSortType = computed(() => store.scheduleSortType);
  const reviewCategory = computed(() => store.reviewCategory);
  const reviewSearchType = computed(() => store.reviewSearchType);
  const reviewSortType = computed(() => store.reviewSortType);

  const hasGenderType = computed(() => store.hasGenderType);
  const hasScheduleCategory = computed(() => store.hasScheduleCategory);
  const hasReviewCategory = computed(() => store.hasReviewCategory);

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

  const getReviewCategory = async () => {
    await store.getReviewCategory();
  };

  const getReviewSearchType = async () => {
    await store.getReviewSearchType();
  };

  const getReviewSortType = async () => {
    await store.getReviewSortType();
  };

  return {
    genderType,
    scheduleSearchType,
    scheduleFilterType,
    scheduleSortType,
    reviewCategory,
    reviewSearchType,
    reviewSortType,
    hasGenderType,
    hasScheduleCategory,
    hasReviewCategory,
    getGenderType,
    getScheduleSearchType,
    getScheduleFilterType,
    getScheduleSortType,
    getReviewCategory,
    getReviewSearchType,
    getReviewSortType,
  };
};
