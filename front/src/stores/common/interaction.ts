import InteractionApiService from "@/services/common/InteractionApiService";
import { defineStore } from "pinia";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";
import type { InteractionType } from "@/modules/types/interaction/InteractionType";

export const useInteractionStore = defineStore("interaction", {
  state: () => ({
    reactedComments: [] as ReactedCommentType[],
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
      await InteractionApiService.reactContent(
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
