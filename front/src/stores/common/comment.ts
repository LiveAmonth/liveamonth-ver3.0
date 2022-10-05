import CommentApiService from "@/services/common/CommentApiService";
import { defineStore } from "pinia";
import type {
  CommentEditor,
  CommentType,
} from "@/modules/types/comment/CommentTypes";
import type {
  PageableRequestType,
  PageableResponseType,
  PageableType,
} from "@/modules/types/common/PageableType";

export const useCommentStore = defineStore("comment", {
  state: () => ({
    pageableComments: {} as PageableResponseType,
    editableComment: {} as CommentType,
  }),
  getters: {
    comments: (state): CommentType[] =>
      state.pageableComments.content as CommentType[],
    commentPage: (state): PageableType => state.pageableComments.pageable,
  },
  actions: {
    writeComment: async function (type: string, request: CommentEditor) {
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
      request: CommentEditor
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

    setEditableComment: function (data: CommentType) {
      this.editableComment = data;
    },
  },
});
