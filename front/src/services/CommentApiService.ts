import http from "@/http-common";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { WriteCommentType } from "@/modules/types/comment/CommentTypes";
import type { TokenType } from "@/modules/types/auth/AuthType";
import type { CommentFormType } from "@/modules/types/form/FormType";

class CommentApiService {
  async writeComment(
    path: string,
    contentId: number,
    commentId = 0,
    tokenInfo: TokenType,
    request: CommentFormType
  ) {
    return await http
      .post(
        `/comments/${path}/${contentId}?commentId=${commentId}`,
        JSON.stringify(request),
        {
          headers: {
            Authorization: `${tokenInfo.grantType} ${tokenInfo.accessToken}`,
          },
        }
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

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
