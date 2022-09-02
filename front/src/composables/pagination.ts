import { computed } from "vue";
import { usePageableStore } from "@/stores/pagination";
import type { PageableResponseType } from "@/modules/types/common/PageableType";
import type Pagination from "@/modules/class/paginations/Pagination";
import type PageableRequest from "@/modules/class/pageables/PageableRequest";

export const usePagination = (type: string) => {
  const store = usePageableStore();
  const pageable = computed(
    (): PageableRequest => store.findPageableRequest(type)
  );
  const pagination = computed((): Pagination => store.findPagination(type));

  const movePage = (page: number): void => {
    store.movePage(type, page);
  };

  const setSort = (sortType: string): void => {
    store.changeSortType(type, sortType);
  };

  const setSize = (limit: number): void => {
    store.setContentLimit(type, limit);
  };

  const getCurrentPageGroupPages = (): number => {
    return store.getCurrentPageGroupPages(type);
  };

  const isCurrentPage = (index: number): boolean => {
    return store.isCurrentPage(type, index);
  };

  const getCurrentPageNumber = (index: number): number => {
    return store.getCurrentPageNumber(type, index);
  };

  const mappingPagination = (data: PageableResponseType): void => {
    store.mappingPagination(type, data);
  };

  return {
    pageable,
    pagination,
    movePage,
    setSort,
    setSize,
    mappingPagination,
    getCurrentPageGroupPages,
    isCurrentPage,
    getCurrentPageNumber,
  };
};
