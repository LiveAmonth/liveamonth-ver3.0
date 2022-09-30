import { useMenuTab } from "@/composables/common/tabs";
import { computed, ref } from "vue";
import { useReviewStore } from "@/stores/review/review";
import type {
  MenuType,
  CategoryMenuType,
} from "@/modules/types/common/MenuType";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { EnumType } from "@/modules/types/common/EnumType";

export const useReview = () => {
  const store = useReviewStore();
  const error = ref();
  const isPending = ref<boolean>(false);

  const { getMenuCategory } = useMenuTab();

  const getReviewMenu = (
    type: EnumType,
    icon: string
  ): CategoryMenuType<MenuType> => {
    return <CategoryMenuType<MenuType>>{
      category: getMenuCategory("review", type.code, icon),
      menus: type.subs?.map((value) => {
        return <MenuType>{
          name: value.code.toLowerCase(),
          sub: [],
          route: {
            name: "review-list",
            params: { menu: value.code.toLowerCase() },
          },
        };
      }),
    };
  };

  const addReview = async (memberId: number, form: any) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.addReview(memberId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editReview = async (reviewId: number, form: any) => {
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

  return {
    isPending,
    getReviewMenu,
    addReview,
    editReview,
    deleteReview,
    getReviews,
    getReview,
  };
};
