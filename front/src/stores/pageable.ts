import { defineStore } from "pinia";
import PageableRequest from "@/modules/class/PageableRequest";
import type { PageableResponseType } from "@/modules/types/common/PageableType";
import Pagination from "@/modules/class/Pagination";

export const usePageableStore = defineStore("pageable", {
  state: () => ({
    request: new PageableRequest(1, 2, "id,desc"),
    pagination: new Pagination(2, 5, 1, true, false, 0, 0),
  }),
  getters: {
    getPagination: (state) => state.pagination,
    getRequest: (state) => state.request,
  },
  actions: {
    movePage(page: number) {
      this.pagination.currentPage = page;
      this.request.page = page;
    },

    changeSortType(sortType: string) {
      this.request.sort = sortType;
    },

    mappingPagination(data: PageableResponseType) {
      this.pagination.mappingPagination(data);
    },
  },
  persist: {
    storage: sessionStorage,
  },
});
