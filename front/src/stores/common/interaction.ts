import InteractionApiService from "@/services/common/InteractionApiService";
import { defineStore } from "pinia";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";
import type { InteractionType } from "@/modules/types/interaction/InteractionType";

export const useInteractionStore = defineStore("interaction", {
  state: () => ({
    reactedComments: [] as ReactedCommentType[],
    isLikedContent: {} as boolean,
  }),
  getters: {},
  actions: {
    getMemberReactedComment: async function (
      type: string,
      memberId: number,
      ids: number[]
    ) {
      await InteractionApiService.getMemberReactedComment(type, memberId, ids)
        .then((response) => {
          this.reactedComments = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    isMemberLikedContent: async function (
      type: string,
      request: InteractionType
    ) {
      await InteractionApiService.isMemberLikeContent(type, request)
        .then((response) => {
          this.isLikedContent = response;
        })
        .catch((error) => {
          throw error;
        });
    },

    changeLikeState: function () {
      this.isLikedContent = !this.isLikedContent;
    },
  },
});
