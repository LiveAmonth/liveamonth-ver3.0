import CommentApiService from "@/services/common/CommentApiService";
import { defineStore } from "pinia";
import type { CommentType } from "@/modules/types/comment/CommentResponse";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/common/PageableType";
import type { CommentFormType } from "@/modules/types/form/FormType";
import type {
  CommentCreateType,
  CommentEditType,
} from "@/modules/types/comment/CommentRequest";
import { CommentEditor } from "@/modules/class/comment/CommentEditor";

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
    writeComment: async function (type: string, request: CommentCreateType) {
      await CommentApiService.writeComment(type, request)
        .then((response) => {
          this.pageableComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    editComment: async function (
      type: string,
      commentId: number,
      request: CommentEditType
    ) {
      await CommentApiService.editComment(type, commentId, request)
        .then((response) => {
          this.pageableComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    deleteComment: async function (type: string, commentId: number) {
      await CommentApiService.deleteComment(type, commentId)
        .then((response) => {
          this.pageableComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },

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
  },
});
