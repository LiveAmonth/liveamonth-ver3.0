import { computed } from "vue";
import { useCategoryStore } from "@/stores/common/category";

export const useCategory = () => {
  const store = useCategoryStore();

  const cityNames = computed(() => store.cityNames);
  const genderType = computed(() => store.genderType);
  const scheduleSearchType = computed(() => store.scheduleSearchType);
  const scheduleFilterType = computed(() => store.scheduleFilterType);
  const scheduleSortType = computed(() => store.scheduleSortType);
  const reviewCategory = computed(() => store.reviewCategory);
  const reviewSearchType = computed(() => store.reviewSearchType);
  const reviewSortType = computed(() => store.reviewSortType);
  const reviewMenuGroup = computed(() => store.reviewMenuGroup);

  const hasCityNames = computed(() => store.hasCityNames);
  const hasGenderType = computed(() => store.hasGenderType);
  const hasScheduleCategory = computed(() => store.hasScheduleCategory);
  const hasReviewCategory = computed(() => store.hasReviewCategory);

  const getCityNames = async () => {
    if (!hasCityNames.value) {
      await store.getCityNames();
    }
  };

  const getGenderType = async () => {
    if (!hasGenderType.value) {
      await store.getGenderType();
    }
  };

  const getScheduleCategories = async () => {
    if (!hasScheduleCategory.value) {
      await store.getScheduleSearchType();
      await store.getScheduleFilterType();
      await store.getScheduleSortType();
    }
  };

  const getReviewCategories = async () => {
    if (!hasReviewCategory.value) {
      await store.getReviewCategory();
      await store.getReviewSearchType();
      await store.getReviewSortType();
      await store.getReviewMenuGroup();
    }
  };

  return {
    cityNames,
    genderType,
    scheduleSearchType,
    scheduleFilterType,
    scheduleSortType,
    reviewCategory,
    reviewSearchType,
    reviewSortType,
    reviewMenuGroup,
    hasCityNames,
    hasGenderType,
    hasScheduleCategory,
    hasReviewCategory,
    getCityNames,
    getGenderType,
    getScheduleCategories,
    getReviewCategories,
  };
};
