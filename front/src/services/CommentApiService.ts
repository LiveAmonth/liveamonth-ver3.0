import http from "@/http-common";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { CommentFormType } from "@/modules/types/form/FormType";

class CommentApiService {
  async writeComment(
    type: string,
    contentId: number,
    commentId: number,
    request: CommentFormType
  ) {
    return await http
      .post(
        `/comments/${contentId}?type=${type}&comment_id=${commentId}`,
        JSON.stringify(request)
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getComments(
    type: string,
    contentId: number,
    pageable: PageableRequestType
  ) {
    return await http
      .get(
        `/comments/${contentId}?type=${type}&page=${pageable.page - 1}&size=${
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
