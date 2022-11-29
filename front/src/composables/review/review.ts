import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useMenuTab } from "@/composables/common/tabs";
import { useReviewStore } from "@/stores/review/review";
import { useCategory } from "@/composables/common/category";
import type {
  MenuType,
  CategoryMenuType,
} from "@/modules/types/common/MenuTypes";
import type {
  PageableRequestType,
  PageableType,
} from "@/modules/types/pagination/PaginationTypes";
import type { EnumType } from "@/modules/types/common/CommonTypes";
import type {
  ReviewDetailType,
  ReviewListType,
  TagType,
} from "@/modules/types/review/ReviewTypes";
import type { ReviewEditor } from "@/modules/types/review/ReviewTypes";
import { DomainType } from "@/modules/enums/constants";

export const useReview = () => {
  const type: string = DomainType.REVIEW;
  const store = useReviewStore();
  const error = ref();
  const isPending = ref<boolean>(false);
  const router = useRouter();
  const { getMenuCategory } = useMenuTab();
  const { cityNames, reviewSearchType } = useCategory();

  const request = computed(() => store.searchCond);
  const reviewPage = computed((): PageableType => store.reviewPage);
  const otherReviews = computed((): ReviewListType[] => store.otherReviews);
  const bestReviews = computed((): ReviewListType[] => store.bestReviews);
  const currReview = computed((): ReviewDetailType => store.currReview);
  const myReviews = computed((): ReviewListType[] => store.myReviews);
  const addedReviewId = computed((): number => store.addedReviewId);
  const recommendationTags = computed(
    (): TagType[] => store.recommendationTags
  );

  const hasMyReviews = computed(() => store.hasMyReviews);
  const hasOtherReviews = computed(() => store.hasOtherReviews);
  const hasBestReviews = computed(() => store.hasBestReviews);

  const cityReviewTabs: string[] = ["TOTAL"];
  cityNames.value.forEach((value) =>
    cityReviewTabs.push(`${value.code}_REVIEW`)
  );

  const getReviewMenu = (
    type: EnumType,
    icon: string
  ): CategoryMenuType<MenuType> => {
    return <CategoryMenuType<MenuType>>{
      category: getMenuCategory("review", type.code.toLowerCase(), icon),
      menus: reviewSearchType.value
        .filter((value) => filterCategory(type.code, value))
        .map((value) => mapToMenu(value)),
    };
  };

  const addReview = async (loginId: string, form: ReviewEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.addReview(loginId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editReview = async (reviewId: number, form: ReviewEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.editReview(reviewId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const deleteReview = async (reviewId: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.deleteReview(reviewId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getReviews = async (pageable: PageableRequestType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getReviews(pageable);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getBestReviews = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getBestReviews();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getMyReviews = async (
    loginId: string,
    size: number,
    lastId: number | null
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getMyReviews(loginId, size, lastId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getReview = async (reviewId: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getReview(reviewId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const mapToMenu = (value: EnumType) => {
    return <MenuType>{
      name: value.code.toLowerCase(),
      sub: [],
      route: {
        name: "review-list",
        params: { menu: value.code.toLowerCase() },
      },
    };
  };

  const filterCategory = (category: string, menu: EnumType) => {
    return category == menu.code.split("_")[0];
  };

  const goReadReview = async (id: string | number) => {
    await router.push({
      name: "read-review",
      params: { id: id },
    });
  };

  const goReview = async (id: string | number) => {
    await store.viewCountUp(Number(id));
    await router.push({
      name: "read-review",
      params: { id: id },
    });
  };

  const goReviewList = async () => {
    await router.push({
      name: "review-list",
      params: { menu: reviewSearchType.value[0].code.toLowerCase() },
    });
  };

  const getRecommendationTags = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getRecommendationTags();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  return {
    isPending,
    error,
    type,
    request,
    reviewPage,
    cityReviewTabs,
    otherReviews,
    bestReviews,
    currReview,
    recommendationTags,
    myReviews,
    addedReviewId,
    hasMyReviews,
    hasOtherReviews,
    hasBestReviews,
    getReviewMenu,
    addReview,
    editReview,
    deleteReview,
    getReviews,
    getBestReviews,
    getMyReviews,
    getReview,
    goReadReview,
    goReview,
    goReviewList,
    getRecommendationTags,
  };
};
