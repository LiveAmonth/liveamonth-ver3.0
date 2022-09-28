import { defineStore } from "pinia";
import Pagination from "@/modules/class/paginations/Pagination";
import type {
  PageableRequestType,
  PageableType,
} from "@/modules/types/common/PageableType";

export const usePageableStore = defineStore("pageable", {
  state: () => ({
    pagination: [
      new Pagination(5, "SCHEDULE"),
      new Pagination(5, "COMMENT"),
      new Pagination(10, "INQUIRY"),
      new Pagination(10, "REVIEW"),
    ] as Pagination[],
  }),
  getters: {},
  actions: {
    findPage: function (type: string): Pagination {
      return this.pagination.find(
        (value) => value.category === type
      ) as Pagination;
    },

    getRequest: function (type: string): PageableRequestType {
      const pagination = this.findPage(type);
      return <PageableRequestType>{
        size: pagination.size,
        page: pagination.currentPage,
        sort: pagination.sort,
      };
    },

    clear: function (type: string) {
      const pagination = this.findPage(type);
      pagination.pageLimit = 5;
      pagination.currentPage = 1;
      pagination.isFirst = true;
      pagination.isLast = false;
      pagination.numberOfContents = 0;
      pagination.numberOfPages = 0;
      pagination.sort = "id,desc";
      pagination.category = type;
    },

    setSize: function (type: string, size: number) {
      this.findPage(type).size = size;
    },

    movePage: function (type: string, page: number) {
      this.findPage(type).currentPage = page;
    },

    changeSortType: function (type: string, sortType: string) {
      const pagination = this.findPage(type);
      pagination.sort = sortType;
      pagination.currentPage = 1;
    },

    currentPageGroup: function (type: string): number {
      const pagination = this.findPage(type);
      return Math.ceil(pagination.currentPage / pagination.pageLimit) - 1;
    },

    numberOfPageGroup: function (type: string): number {
      const pagination = this.findPage(type);
      return Math.ceil(pagination.numberOfPages / pagination.pageLimit);
    },

    getCurrentPageGroupPages: function (type: string): number {
      const pagination = this.findPage(type);
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

    isCurrentPage: function (type: string, index: number): boolean {
      const pagination = this.findPage(type);
      return (
        this.currentPageGroup(type) * pagination.pageLimit + index ===
        pagination.currentPage
      );
    },

    getCurrentPageNumber: function (type: string, index: number): number {
      return (
        index + this.currentPageGroup(type) * this.findPage(type).pageLimit
      );
    },

    mappingPagination: function (type: string, data: PageableType): void {
      const pagination = this.findPage(type);
      pagination.numberOfContents = data.totalElements;
      pagination.numberOfPages = data.totalPages;
      pagination.isFirst = data.first;
      pagination.isLast = data.last;
    },
  },
  // persist: {
  //   storage: sessionStorage,
  // },
});
