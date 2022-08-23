import { computed, onMounted, ref } from "vue";
import type {
  CustomPaginationType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import { usePageableStore } from "@/stores/pageable";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import PageableRequest from "@/modules/class/PageableRequest";

export const usePagination = () => {
  const store = usePageableStore();
  const pageable = computed((): PageableRequestType => store.getRequest);
  const pagination = computed((): CustomPaginationType => store.getPagination);

  const mappingPagination = (data: PageableResponseType): void => {
    store.mappingPagination(data);
  };

  const movePage = (page: number) => {
    store.movePage(page);
  };

  const setSort = (sortType: string) => {
    store.changeSortType(sortType);
  };

  return {
    pageable,
    pagination,
    mappingPagination,
    movePage,
    setSort,
  };
};
