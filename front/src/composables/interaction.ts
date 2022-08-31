import { useMember } from "@/composables/member";
import { computed, ref } from "vue";
import InteractionApiService from "@/services/InteractionApiService";
import { useInteractionStore } from "@/stores/interaction";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";
import { useI18n } from "vue-i18n";

export const useInteraction = () => {
  const error = ref();
  const isPending = ref(false);
  const store = useInteractionStore();
  const { t } = useI18n();
  const { simpleProfile } = useMember();
  const isLike = ref(false);
  const reactedComments = computed(() => store.reactedComments);
  const getReactedComment = async (
    id: number
  ): Promise<ReactedCommentType | undefined> => {
    return reactedComments.value.find((value) => value.id === id);
  };

  const checkReacted = async (option: boolean, type: string | undefined) => {
    if (option && type && type === "DISLIKE") {
      error.value = t("interaction.alreadyDislikeComment");
      throw error.value;
    } else if (!option && type && type === "LIKE") {
      error.value = t("interaction.alreadyLikeComment");
      throw error.value;
    }
  };

  const likeContent = async (type: string, contentId: number) => {
    await InteractionApiService.likeContent(type, {
      from: simpleProfile.value.id,
      to: contentId,
    })
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const cancelLikeContent = async (type: string, contentId: number) => {
    await InteractionApiService.cancelLikeContent(type, {
      from: simpleProfile.value.id,
      to: contentId,
    })
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const isLoggedInMemberLikes = async (type: string, contentId: number) => {
    await InteractionApiService.isMemberLikeContent(type, {
      from: simpleProfile.value.id,
      to: contentId,
    })
      .then((response) => {
        isLike.value = response;
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const getMemberReactedComment = async (type: string, ids: number[]) => {
    try {
      await store.getMemberReactedComment(type, simpleProfile.value.id, ids);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const reactComment = async (
    commentType: string,
    commentId: number,
    option: boolean,
    isReacted: boolean
  ) => {
    await InteractionApiService.reactComment(
      commentType,
      option ? "LIKE" : "DISLIKE",
      {
        from: simpleProfile.value.id,
        to: commentId,
      },
      isReacted
    )
      .then((response) => {
        console.log(response);
      })
      .catch((err) => {
        error.value = err;
      });
  };

  return {
    error,
    isPending,
    isLike,
    reactedComments,
    checkReacted,
    getReactedComment,
    likeContent,
    reactComment,
    cancelLikeContent,
    isLoggedInMemberLikes,
    getMemberReactedComment,
  };
};
