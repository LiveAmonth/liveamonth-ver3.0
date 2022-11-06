import InteractionApiService from "@/services/common/InteractionApiService";
import { computed, ref } from "vue";
import { useMember } from "@/composables/member/member";
import { useInteractionStore } from "@/stores/common/interaction";
import { useMessageBox } from "@/composables/common/messageBox";
import type {
  InteractionType,
  CommentInteractionType,
} from "@/modules/types/interaction/InteractionType";

export const useInteraction = () => {
  const store = useInteractionStore();

  const { simpleProfile } = useMember();
  const { validationMsg } = useMessageBox();
  const error = ref();
  const isPending = ref(false);
  const isLiked = computed(() => store.isLikedContent);
  const isFollowed = computed(() => store.isFollowedMember);
  const interactedComments = computed(() => store.interactedComments);
  const heartImg = "https://i.ibb.co/zxHVVSS/love.png";
  const heartFillImg = "https://i.ibb.co/p3x6KVk/love-fill.png";

  const getInteractionRequest = (id: number): InteractionType => {
    return {
      from: simpleProfile.value.id,
      to: id,
    };
  };

  const interactContent = async (type: string, toId: number) => {
    try {
      isPending.value = true;
      await store.doInteraction(
        type,
        simpleProfile.value.loginId,
        getInteractionRequest(toId)
      );
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const isPositiveInteraction = async (type: string, contentId: number) => {
    try {
      await store.isMemberLikedContent(type, getInteractionRequest(contentId));
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getInteractedCommentsByMember = async (type: string, ids: number[]) => {
    try {
      await store.getInteractedCommentsByMember(
        type,
        simpleProfile.value.id,
        ids
      );
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const interactComment = async (
    commentType: string,
    commentId: number,
    option: boolean,
    isInteracted: boolean
  ) => {
    await InteractionApiService.interactComment(
      commentType,
      simpleProfile.value.loginId,
      option ? "LIKE" : "DISLIKE",
      getInteractionRequest(commentId),
      isInteracted
    )
      .then(() => {
        console.log("interact comment");
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const getInteractedComment = async (
    id: number
  ): Promise<CommentInteractionType | undefined> => {
    return interactedComments.value.find((value) => value.id === id);
  };

  const checkInteraction = async (
    option: boolean,
    type: string | undefined
  ) => {
    if (option && type && type === "DISLIKE") {
      error.value = validationMsg("interaction.alreadyDislikeComment");
      throw error.value;
    } else if (!option && type && type === "LIKE") {
      error.value = validationMsg("interaction.alreadyLikeComment");
      throw error.value;
    }
  };

  const isWriter = (toId: number) => {
    return simpleProfile.value.id == toId;
  };

  return {
    error,
    isPending,
    heartImg,
    heartFillImg,
    isLiked,
    isFollowed,
    interactedComments,
    checkInteraction,
    getInteractedComment,
    interactContent,
    interactComment,
    isPositiveInteraction,
    getInteractedCommentsByMember,
    isWriter,
  };
};
