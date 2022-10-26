package teamproject.lam_server.domain.review.service;

import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

public interface ReviewService {

    PostIdResponse write(String loginId, ReviewCreate request);

    CustomPage<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO);

    ReviewDetailResponse getReview(Long id);

    void viewCountUp(Long id);

    void edit(Long id, ReviewEdit reviewEdit);

    void delete(Long id);

    List<ReviewListResponse> getReviewByMember(String loginId, Integer size, Long lastId);
}
