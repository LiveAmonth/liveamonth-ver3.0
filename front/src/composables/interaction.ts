import { useMember } from "@/composables/member";
import { computed, ref } from "vue";
import InteractionApiService from "@/services/InteractionApiService";
import { useInteractionStore } from "@/stores/interaction";
import type {
  InteractionType,
  ReactedCommentType,
} from "@/modules/types/interaction/InteractionType";
import { useI18n } from "vue-i18n";

export const useInteraction = () => {
  const store = useInteractionStore();

  const { t } = useI18n();
  const { simpleProfile } = useMember();

  const error = ref();
  const isPending = ref(false);
  const isLiked = computed(() => store.isLikedContent);
  const reactedComments = computed(() => store.reactedComments);

  const getInteractionRequest = (id: number): InteractionType => {
    return {
      from: simpleProfile.value.id,
      to: id,
    };
  };

  const reactContent = async (type: string, contentId: number) => {
    await InteractionApiService.reactContent(
      type,
      isLiked.value,
      getInteractionRequest(contentId)
    )
      .then(() => {
        console.log("react content");
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const isLikedContent = async (type: string, contentId: number) => {
    try {
      await store.isMemberLikedContent(type, getInteractionRequest(contentId));
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
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
      getInteractionRequest(commentId),
      isReacted
    )
      .then(() => {
        console.log("react comment");
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const getReactedComment = async (
    id: number
  ): Promise<ReactedCommentType | undefined> => {
    return reactedComments.value.find((value) => value.id === id);
  };

  const checkReacted = async (option: boolean, type: string | undefined) => {
    if (option && type && type === "DISLIKE") {
      error.value = t("validation.interaction.alreadyDislikeComment");
      throw error.value;
    } else if (!option && type && type === "LIKE") {
      error.value = t("validation.interaction.alreadyLikeComment");
      throw error.value;
    }
  };

  const changeLikeState = () => {
    store.changeLikeState();
  };
  return {
    error,
    isPending,
    isLiked,
    reactedComments,
    checkReacted,
    getReactedComment,
    reactContent,
    reactComment,
    isLikedContent,
    getMemberReactedComment,
    changeLikeState,
  };
};
