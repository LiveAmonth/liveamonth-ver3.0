import { computed, ref } from "vue";
import type {
  CustomPaginationType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";

export const usePagination = () => {
  const pagination: CustomPaginationType = {
    numberOfPages: ref<number>(0),
    numberOfContents: ref<number>(0),
    contentLimit: 4,
    pageLimit: 5,
    currentPage: ref<number>(1),
    pageGroup: computed(() => {
      return Math.ceil(pagination.currentPage.value / pagination.pageLimit) - 1;
    }),
    numberOfPageGroup: computed(() => {
      return Math.ceil(pagination.numberOfPages.value / pagination.pageLimit);
    }),
    isFirst: ref<boolean>(true),
    isLast: ref<boolean>(false),
  };
  const mappingPagination = (data: PageableResponseType) => {
    pagination.numberOfContents.value = data.totalElements;
    pagination.numberOfPages.value = data.totalPages;
    pagination.isFirst.value = data.first;
    pagination.isLast.value = data.last;
  };

  const isCurrPage = (
    pageGroup: number,
    currPage: number,
    page: number
  ): boolean => {
    return pageGroup * pagination.pageLimit + page === currPage;
  };

  const calcNumberOfPageGroup = (
    pageGroup: number,
    numberOfPageGroup: number,
    numberOfPages: number
  ): number => {
    return pageGroup !== numberOfPageGroup - 1
      ? pagination.pageLimit
      : numberOfPages - pagination.pageLimit * pageGroup;
  };

  const getCurrPageNumber = (pageGroup: number, page: number): number => {
    return page + pageGroup * pagination.pageLimit;
  };
  return {
    pagination,
    isCurrPage,
    calcNumberOfPageGroup,
    getCurrPageNumber,
    mappingPagination,
  };
};
