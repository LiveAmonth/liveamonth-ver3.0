import { defineStore } from "pinia";
import type { CommentType } from "@/modules/types/comment/CommentTypes";
import type {
  PageableRequestType,
  PageableResponseType,
} from "@/modules/types/common/PageableType";
import type { CommentFormType } from "@/modules/types/form/FormType";
import CommentApiService from "@/services/CommentApiService";

export const useCommentStore = defineStore("comment", {
  state: () => ({
    pageableComments: {} as PageableResponseType,
  }),
  getters: {
    comments: (state): CommentType[] =>
      state.pageableComments.content as CommentType[],
  },
  actions: {
    async getComments(
      type: string,
      contentId: number,
      pageable: PageableRequestType
    ) {
      await CommentApiService.getComments(type, contentId, pageable)
        .then((response) => {
          this.pageableComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    async writeComment(
      type: string,
      loginId: string,
      request: CommentFormType
    ) {
      await CommentApiService.writeComment(type, loginId, request)
        .then((response) => {
          this.pageableComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
