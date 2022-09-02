import { defineStore } from "pinia";
import PageableRequest from "@/modules/class/pageables/PageableRequest";
import Pagination from "@/modules/class/paginations/Pagination";
import type { PageableResponseType } from "@/modules/types/common/PageableType";

export const usePageableStore = defineStore("pageable", {
  state: () => ({
    requests: [
      new PageableRequest(1, 3, "id,desc", "SCHEDULE"),
      new PageableRequest(1, 5, "id,desc", "COMMENT"),
    ] as PageableRequest[],
    paginations: [
      new Pagination(3, 5, 1, true, false, 0, 0, "SCHEDULE"),
      new Pagination(5, 5, 1, true, false, 0, 0, "COMMENT"),
    ] as Pagination[],
  }),
  getters: {},
  actions: {
    findPageableRequest(type: string): PageableRequest {
      return this.requests.find(
        (value) => value.category === type
      ) as PageableRequest;
    },

    findPagination(type: string): Pagination {
      return this.paginations.find(
        (value) => value.category === type
      ) as Pagination;
    },

    setContentLimit(type: string, limit: number) {
      this.findPageableRequest(type).size = limit;
      this.findPagination(type).contentLimit = limit;
    },

    movePage(type: string, page: number) {
      (this.findPagination(type) as Pagination).currentPage = page;
      this.findPageableRequest(type).page = page;
    },

    changeSortType(type: string, sortType: string) {
      this.findPageableRequest(type).sort = sortType;
    },

    currentPageGroup(type: string): number {
      const pagination = this.findPagination(type);
      return Math.ceil(pagination.currentPage / pagination.pageLimit) - 1;
    },

    numberOfPageGroup(type: string): number {
      const pagination = this.findPagination(type);
      return Math.ceil(pagination.numberOfPages / pagination.pageLimit);
    },

    getCurrentPageGroupPages(type: string): number {
      const pagination = this.findPagination(type);
      if (pagination.numberOfPages < pagination.pageLimit) {
        return pagination.numberOfPages;
      } else if (
        this.currentPageGroup(type) ===
        this.numberOfPageGroup(type) - 1
      ) {
        return (
          pagination.numberOfPages -
          pagination.pageLimit * this.currentPageGroup(type)
        );
      } else {
        return pagination.pageLimit;
      }
    },

    isCurrentPage(type: string, index: number): boolean {
      const pagination = this.findPagination(type);
      return (
        this.currentPageGroup(type) * pagination.pageLimit + index ===
        pagination.currentPage
      );
    },

    getCurrentPageNumber(type: string, index: number): number {
      return (
        index +
        this.currentPageGroup(type) * this.findPagination(type).pageLimit
      );
    },

    mappingPagination(type: string, data: PageableResponseType): void {
      const pagination = this.findPagination(type);
      pagination.numberOfContents = data.totalElements;
      pagination.numberOfPages = data.totalPages;
      pagination.isFirst = data.first;
      pagination.isLast = data.last;
    },
  },
  persist: {
    storage: sessionStorage,
  },
});
