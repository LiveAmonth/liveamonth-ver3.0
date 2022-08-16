import { defineStore } from "pinia";
import PageableRequest from "@/modules/class/PageableRequest";
import type { PageableResponseType } from "@/modules/types/common/PageableType";
import Pagination from "@/modules/class/Pagination";

const contentLimit = 2;
const firstPage = 1;
export const usePageableStore = defineStore("pageable", {
  state: () => ({
    request: new PageableRequest(firstPage, contentLimit, "id,desc"),
    pagination: new Pagination(contentLimit, 5, firstPage, true, false, 0, 0),
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

    mappingPagination(data: PageableResponseType) {
      this.pagination.mappingPagination(data);
    },
  },
  persist: true,
});
