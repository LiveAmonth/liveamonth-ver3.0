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

  const hasScheduleCategories = () => {
    return (
      scheduleSearchType.value &&
      scheduleFilterType.value &&
      scheduleSortType.value
    );
  };

  const hasReviewCategories = () => {
    return (
      reviewCategory.value && reviewSearchType.value && reviewSortType.value
    );
  };

  return {
    genderType,
    scheduleSearchType,
    scheduleFilterType,
    scheduleSortType,
    reviewCategory,
    reviewSearchType,
    reviewSortType,
    getGenderType,
    getScheduleSearchType,
    getScheduleFilterType,
    getScheduleSortType,
    getReviewCategory,
    getReviewSearchType,
    getReviewSortType,
    hasScheduleCategories,
    hasReviewCategories,
  };
};
