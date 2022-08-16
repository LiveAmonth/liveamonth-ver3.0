import { computed, ref } from "vue";
import type {
  CustomPaginationType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import { usePageableStore } from "@/stores/pageable";
import type { PageableRequestType } from "@/modules/types/common/PageableType";

export const usePagination = () => {
  const store = usePageableStore();
  const pageable = computed((): PageableRequestType => store.getRequest);
  const pagination = computed((): CustomPaginationType => store.getPagination);

  const mappingPagination = (data: PageableResponseType): void => {
    store.mappingPagination(data);
  };
  const movePage = (page: number) => {
    pagination.value.currentPage = page;
    pageable.value.page = page;
  };
  const setSort = (sortType: string): void => {
    pageable.value.sort = sortType;
  };

  return {
    pageable,
    pagination,

    mappingPagination,
    movePage,
    setSort,
  };
};
