import http from "@/http-common";

class InteractionApiService {
  async isMemberLikeContent(
    token: string,
    type: string,
    contentId: number
  ): Promise<boolean> {
    return await http
      .get(`interactions/member/likes?type=${type}&content_id=${contentId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async likeContent(
    token: string,
    type: string,
    contentId: number
  ): Promise<void> {
    return await http
      .get(`interactions/member/likes?type=${type}&content_id=${contentId}`, {
        headers: {
          Authorization: token,
        },
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
