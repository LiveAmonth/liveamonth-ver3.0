import { defineStore } from "pinia";
import PageableRequest from "@/modules/class/PageableRequest";
import type { PageableResponseType } from "@/modules/types/common/PageableType";
import Pagination from "@/modules/class/Pagination";
import SchedulePageableRequest from "@/modules/class/SchedulePageableRequest";
import CommentPageableRequest from "@/modules/class/CommentPageableRequest";
import type { PageableRequestType } from "@/modules/types/common/PageableType";

export const usePageableStore = defineStore("pageable", {
  state: () => ({
    request: new SchedulePageableRequest(),
    pagination: new Pagination(2, 5, 1, true, false, 0, 0),
    requests: [new SchedulePageableRequest(), new CommentPageableRequest()],
  }),
  getters: {
    getPagination: (state) => state.pagination,
    getRequest: (state) => state.request,
  },
  actions: {
    findPageableRequest(type: string): PageableRequest {
      return <PageableRequest>(
        this.requests.find((value) => value.getType() === type)
      );
    },

    setContentLimit(limit: number) {
      this.findPageableRequest("schedule").size = limit;
      this.pagination.contentLimit = limit;
      // this.request.size = limit;
    },

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
});
