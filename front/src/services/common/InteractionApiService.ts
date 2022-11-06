import http from "@/http-common";
import type {
  InteractionType,
  CommentInteractionType,
} from "@/modules/types/interaction/InteractionType";

class InteractionApiService {
  async interactContent(
    type: string,
    loginId: string,
    isInteracted: boolean,
    request: InteractionType
  ): Promise<string> {
    return await http
      .post(
        `interactions/contents/${type}/${loginId}`,
        JSON.stringify(request),
        {
          params: { is_interacted: isInteracted },
        }
      )
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async interactComment(
    commentType: string,
    loginId: string,
    option: string,
    request: InteractionType,
    isInteracted: boolean
  ): Promise<void> {
    return await http
      .post(
        `interactions/comments/${commentType}/${loginId}${
          isInteracted ? "/cancel" : `?interaction_state=${option}`
        }`,
        JSON.stringify(request)
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async isMemberLikeContent(
    type: string,
    request: InteractionType
  ): Promise<boolean> {
    return await http
      .get(`interactions/member/${type}/liked`, { params: request })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getInteractedCommentsByMember(
    type: string,
    memberId: number,
    request: number[]
  ): Promise<CommentInteractionType[]> {
    return await http
      .get(`interactions/member/${memberId}/interacted-comments/${type}`, {
        params: { ids: request },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new InteractionApiService();
