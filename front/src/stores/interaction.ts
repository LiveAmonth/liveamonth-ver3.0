import { defineStore } from "pinia";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";
import InteractionApiService from "@/services/InteractionApiService";

export const useInteractionStore = defineStore("interaction", {
  state: () => ({
    reactedComments: [] as ReactedCommentType[],
  }),
  getters: {
    getReacted: (state) => (id: number) =>
      state.reactedComments.find((value) => value.id === id),
  },
  actions: {
    async getMemberReactedComment(
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
  },
});
