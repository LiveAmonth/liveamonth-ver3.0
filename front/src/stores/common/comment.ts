import CommentApiService from "@/services/common/CommentApiService";
import { defineStore } from "pinia";
import type { CommentType } from "@/modules/types/comment/CommentTypes";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/common/PageableType";
import type { CommentFormType } from "@/modules/types/form/FormType";

export const useCommentStore = defineStore("comment", {
  state: () => ({
    pageableComments: {} as PageableResponseType,
  }),
  getters: {
    comments: (state): CommentType[] =>
      state.pageableComments.content as CommentType[],
    commentPage: (state): PageableType => state.pageableComments.pageable,
  },
  actions: {
    getComments: async function (
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

    writeComment: async function (
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