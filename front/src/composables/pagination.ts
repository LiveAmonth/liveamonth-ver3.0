import { computed } from "vue";
import { usePageableStore } from "@/stores/pagination";
import type {
  PageableType,
  PageableRequestType,
} from "@/modules/types/common/PageableType";
import type Pagination from "@/modules/class/paginations/Pagination";

export const usePagination = (type: string) => {
  const store = usePageableStore();

  const pageable = computed((): PageableRequestType => store.getRequest(type));
  const pagination = computed((): Pagination => store.findPage(type));

  const movePage = (page: number): void => {
    store.movePage(type, page);
  };

  const clear = (): void => {
    store.clear(type);
  };

  const setSort = (sortType: string): void => {
    store.changeSortType(type, sortType);
  };

  const setSize = (limit: number): void => {
    store.setSize(type, limit);
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

  const mappingPagination = (data: PageableType): void => {
    store.mappingPagination(type, data);
  };

  return {
    pageable,
    pagination,
    movePage,
    clear,
    setSort,
    setSize,
    mappingPagination,
    getCurrentPageGroupPages,
    isCurrentPage,
    getCurrentPageNumber,
  };
};
