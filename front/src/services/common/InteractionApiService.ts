import http from "@/http-common";
import type {
  InteractionType,
  ReactedCommentType,
} from "@/modules/types/interaction/InteractionType";

class InteractionApiService {
  async reactContent(
    type: string,
    loginId: string,
    isReacted: boolean,
    request: InteractionType
  ): Promise<string> {
    return await http
      .post(
        `interactions/contents/${type}/${loginId}`,
        JSON.stringify(request),
        {
          params: { is_reacted: isReacted },
        }
      )
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async reactComment(
    commentType: string,
    loginId: string,
    option: string,
    request: InteractionType,
    isReacted: boolean
  ): Promise<void> {
    return await http
      .post(
        `interactions/comments/${commentType}/${loginId}${
          isReacted ? "/cancel" : `?react_type=${option}`
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

  async getMemberReactedComment(
    type: string,
    memberId: number,
    request: number[]
  ): Promise<ReactedCommentType[]> {
    return await http
      .get(`interactions/member/${memberId}/reacted-comments/${type}`, {
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
