import http, { getSortTypes, getSearchTypes } from "@/http-common";
import type { PageableRequestType } from "@/modules/types/pagination/PaginationTypes";
import type {
  ReviewEditor,
  ReviewSearchCond,
} from "@/modules/types/review/ReviewTypes";
import type { IdResponseType } from "@/modules/types/common/CommonTypes";

class ReviewApiService {
  async getReviewCategory() {
    return await http
      .get("/categories/review")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getReviewMenuGroup() {
    return await http
      .get("/categories/review/group")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getSearchTypes() {
    return await getSearchTypes("review")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getSortTypes() {
    return await getSortTypes("review")
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async addReview(
    loginId: string,
    form: ReviewEditor
  ): Promise<IdResponseType> {
    return await http
      .post(`/reviews/${loginId}`, JSON.stringify(form.getCreateData()))
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editReview(reviewId: number, form: ReviewEditor): Promise<string> {
    return await http
      .patch(`/reviews/${reviewId}`, JSON.stringify(form.getEditData()))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async deleteReview(reviewId: number): Promise<string> {
    return await http
      .delete(`/reviews/${reviewId}`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getReviews(request: ReviewSearchCond, pageable: PageableRequestType) {
    return await http
      .get(
        `/reviews/search?page=${pageable.page - 1}&size=${pageable.size}&sort=${
          pageable.sort
        }`,
        {
          params: request.getSearchData(),
        }
      )
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getMyReviews(
    loginId: string,
    size: number,
    lastId: number | null = null
  ) {
    return await http
      .get(`/reviews/list/${loginId}`, {
        params: {
          size: size,
          last_id: lastId != null ? lastId : null,
        },
      })
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async getReview(reviewId: number) {
    return await http
      .get(`/reviews/${reviewId}/detail`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  viewCountUp(reviewId: number): Promise<void> {
    return http
      .patch(`/reviews/${reviewId}/count-up`, {})
      .then((response) => {
        console.log(response.data.message);
      })
      .catch((error) => {
        console.log(error.response.data);
      });
  }
}

export default new ReviewApiService();
