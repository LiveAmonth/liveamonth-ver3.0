import http from "@/http-common";
import type {
  InteractionType,
  ReactedCommentType,
} from "@/modules/types/interaction/InteractionType";

class InteractionApiService {
  async likeContent(type: string, request: InteractionType): Promise<void> {
    return await http
      .post(`interactions/like?type=${type}`, JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
  async reactComment(
    commentType: string,
    reactType: string,
    request: InteractionType,
    isReacted: boolean
  ): Promise<void> {
    return await http
      .post(
        `interactions/comments/react${
          isReacted ? "/cancel" : ""
        }?comment_type=${commentType}&react_type=${reactType}`,
        JSON.stringify(request)
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async cancelLikeContent(
    type: string,
    request: InteractionType
  ): Promise<void> {
    return await http
      .post(`interactions/like/cancel?type=${type}`, JSON.stringify(request))
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
      .get(`interactions/member/liked?type=${type}`, { params: request })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async isMemberLikeComment(
    type: string,
    request: InteractionType
  ): Promise<boolean> {
    return await http
      .get(`interactions/member/like-comments?type=${type}`)
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
      .get(`interactions/member/${memberId}/reacted-comments?type=${type}`, {
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
