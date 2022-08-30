import { useMember } from "@/composables/member";
import { ref } from "vue";
import InteractionApiService from "@/services/InteractionApiService";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";

export const useInteraction = () => {
  const error = ref();
  const isPending = ref(false);
  const { simpleProfile } = useMember();
  const isLike = ref(false);
  const reactedComments = ref<ReactedCommentType[]>([]);

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
    await InteractionApiService.getMemberReactedComment(
      type,
      simpleProfile.value.id,
      ids
    )
      .then((response) => {
        reactedComments.value = response;
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const reactComment = async (
    commentType: string,
    reactType: boolean,
    commentId: number,
    isReacted: boolean
  ) => {
    await InteractionApiService.reactComment(
      commentType,
      reactType ? "LIKE" : "DISLIKE",
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
    likeContent,
    reactComment,
    cancelLikeContent,
    isLoggedInMemberLikes,
    getMemberReactedComment,
  };
};
