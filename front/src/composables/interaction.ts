import { useMember } from "@/composables/member";
import { ref } from "vue";
import InteractionApiService from "@/services/InteractionApiService";

export const useInteraction = () => {
  const error = ref();
  const isPending = ref(false);
  const { simpleProfile } = useMember();
  const isLike = ref(false);

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
  return {
    error,
    isPending,
    isLike,
    likeContent,
    cancelLikeContent,
    isLoggedInMemberLikes,
  };
};
