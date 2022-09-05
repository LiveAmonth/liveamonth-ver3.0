import http from "@/http-common";
import type {
  InteractionType,
  ReactedCommentType,
} from "@/modules/types/interaction/InteractionType";

class InteractionApiService {
  async reactContent(
    type: string,
    isLiked: boolean,
    request: InteractionType
  ): Promise<void> {
    return await http
      .post(`interactions/contents/${type}`, JSON.stringify(request), {
        params: { is_liked: isLiked },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async reactComment(
    commentType: string,
    option: string,
    request: InteractionType,
    isReacted: boolean
  ): Promise<void> {
    return await http
      .post(
        `interactions/comments/${commentType}/${
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
