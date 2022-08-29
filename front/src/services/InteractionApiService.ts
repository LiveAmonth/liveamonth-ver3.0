import http from "@/http-common";
import type { InteractionType } from "@/modules/types/interaction/InteractionType";

class InteractionApiService {
  async isMemberLikeContent(
    type: string,
    request: InteractionType
  ): Promise<boolean> {
    console.log(request);
    return await http
      .post(`interactions/member/likes?type=${type}`, JSON.stringify(request))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

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
}

export default new InteractionApiService();
