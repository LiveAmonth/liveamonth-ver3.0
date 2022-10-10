import http from "@/http-common";
import type { PageableRequestType } from "@/modules/types/pagination/PaginationTypes";
import type { CommentEditor } from "@/modules/types/comment/CommentTypes";

class CommentApiService {
  async writeComment(type: string, request: CommentEditor) {
    return await http
      .post(
        `/comments/${type}/${request.contentId}`,
        JSON.stringify(request.getCreateData())
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editComment(type: string, commentId: number, request: CommentEditor) {
    return await http
      .patch(
        `/comments/${type}/${commentId}`,
        JSON.stringify(request.getEditData())
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
  async deleteComment(type: string, commentId: number) {
    return await http
      .delete(`/comments/${type}/${commentId}`)
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
      .get(`/comments/${type}/${contentId}`, {
        params: {
          page: pageable.page - 1,
          size: pageable.size,
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

export default new CommentApiService();
