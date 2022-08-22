import http from "@/http-common";
import type { PageableRequestType } from "@/modules/types/common/PageableType";

class CommentApiService {
  async getComments(
    path: string,
    contentId: number,
    pageable: PageableRequestType
  ) {
    return await http
      .get(
        `/comments/${path}/${contentId}?page=${pageable.page - 1}&size=${
          pageable.size
        }`
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new CommentApiService();
