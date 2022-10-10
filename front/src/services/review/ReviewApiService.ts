import http, { getSortTypes, getSearchTypes } from "@/http-common";
import type { PageableRequestType } from "@/modules/types/pagination/PaginationTypes";
import type { ReviewSearchCond } from "@/modules/types/review/ReviewTypes";

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

  async addReview(memberId: number, request: any): Promise<string> {
    return await http
      .post(`/reviews/${memberId}`, JSON.stringify(request))
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }

  async editReview(reviewId: number, request: any): Promise<string> {
    return await http
      .patch(`/reviews/${reviewId}`, JSON.stringify(request))
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
    console.log(request);
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

  async getReview(reviewId: number) {
    return await http
      .get(`reviews/${reviewId}`)
      .then((response) => {
        return response.data.data;
      })
      .catch((error) => {
        throw error.response.data;
      });
  }
}

export default new ReviewApiService();
