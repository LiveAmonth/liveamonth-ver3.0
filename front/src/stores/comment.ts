import { defineStore } from "pinia";
import type { CommentType } from "@/modules/types/comment/CommentTypes";
import type { PageableResponseType } from "@/modules/types/common/PageableType";
import CommentApiService from "@/services/CommentApiService";
import type { PageableRequestType } from "@/modules/types/common/PageableType";

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
      path: string,
      contentId: number,
      pageable: PageableRequestType
    ) {
      await CommentApiService.getComments(path, contentId, pageable)
        .then((response) => {
          this.pageableComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
