import InteractionApiService from "@/services/common/InteractionApiService";
import { defineStore } from "pinia";
import type { CommentInteractionType } from "@/modules/types/interaction/InteractionType";
import type { InteractionType } from "@/modules/types/interaction/InteractionType";

export const useInteractionStore = defineStore("interaction", {
  state: () => ({
    interactedComments: [] as CommentInteractionType[],
    isLikedContent: false,
    isFollowedMember: false,
  }),
  getters: {},
  actions: {
    doInteraction: async function (
      type: string,
      loginId: string,
      request: InteractionType
    ) {
      await InteractionApiService.interactContent(
        type,
        loginId,
        this.isTypeMember(type) ? this.isFollowedMember : this.isLikedContent,
        request
      )
        .then((response) => {
          console.log(response);
          this.changeLikeState(type);
        })
        .catch((error) => {
          throw error;
        });
    },

    getInteractedCommentsByMember: async function (
      type: string,
      memberId: number,
      ids: number[]
    ) {
      await InteractionApiService.getInteractedCommentsByMember(
        type,
        memberId,
        ids
      )
        .then((response) => {
          this.interactedComments = response;
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
          this.isTypeMember(type)
            ? (this.isFollowedMember = response)
            : (this.isLikedContent = response);
        })
        .catch((error) => {
          throw error;
        });
    },

    changeLikeState: function (type: string) {
      this.isTypeMember(type)
        ? (this.isFollowedMember = !this.isFollowedMember)
        : (this.isLikedContent = !this.isLikedContent);
    },

    isTypeMember: function (type: string) {
      return type === "member";
    },
  },
});
