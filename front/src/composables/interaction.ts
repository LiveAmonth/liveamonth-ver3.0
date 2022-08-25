import { useAuth } from "@/composables/auth";
import { useMember } from "@/composables/member";
import { ref } from "vue";
import InteractionApiService from "@/services/InteractionApiService";

export const useInteraction = () => {
  const error = ref();
  const isPending = ref(false);
  const { bearerToken } = useAuth();
  const { simpleProfile } = useMember();

  const isLike = ref(false);
  const isLoggedInMemberLikes = async (type: string, contentId: number) => {
    await InteractionApiService.isMemberLikeContent(
      bearerToken.value,
      type,
      contentId
    )
      .then((response) => {
        isLike.value = response;
      })
      .catch((err) => {
        error.value = err;
      });
  };

  const likeContent = async (type: string, request: InteractionType) => {
    await InteractionApiService.likeContent(bearerToken.value, type, contentId)
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
    isLoggedInMemberLikes,
  };
};
