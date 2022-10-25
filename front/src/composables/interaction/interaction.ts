import InteractionApiService from "@/services/common/InteractionApiService";
import { computed, ref } from "vue";
import { useMember } from "@/composables/member/member";
import { useInteractionStore } from "@/stores/common/interaction";
import { useMessageBox } from "@/composables/common/messageBox";
import type {
  InteractionType,
  ReactedCommentType,
} from "@/modules/types/interaction/InteractionType";

export const useInteraction = () => {
  const store = useInteractionStore();

  const { simpleProfile } = useMember();
  const { validationMsg } = useMessageBox();
  const error = ref();
  const isPending = ref(false);
  const isLiked = computed(() => store.isLikedContent);
  const isFollowed = computed(() => store.isFollowedMember);
  const reactedComments = computed(() => store.reactedComments);

  const getInteractionRequest = (id: number): InteractionType => {
    return {
      from: simpleProfile.value.id,
      to: id,
    };
  };

  const reactContent = async (type: string, toId: number) => {
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
      simpleProfile.value.loginId,
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
    isLiked,
    isFollowed,
    reactedComments,
    checkReacted,
    getReactedComment,
    reactContent,
    reactComment,
    isPositiveInteraction,
    getMemberReactedComment,
    isWriter,
  };
};
