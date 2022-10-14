import { computed } from "vue";
import { useCategoryStore } from "@/stores/common/category";

export const useCategory = () => {
  const store = useCategoryStore();
  const popularSort = "LIKE_DESC";

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

  const schedulePopularSortType = computed(
    () =>
      store.scheduleSortType.find((value) => value.code == popularSort)
        ?.title as string
  );

  const reviewPopularSortType = computed(
    () =>
      store.reviewSortType.find((value) => value.code == popularSort)
        ?.title as string
  );

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
    schedulePopularSortType,
    reviewPopularSortType,
    getCityNames,
    getGenderType,
    getScheduleCategories,
    getReviewCategories,
  };
};
