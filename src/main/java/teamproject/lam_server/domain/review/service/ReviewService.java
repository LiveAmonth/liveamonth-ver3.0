package teamproject.lam_server.domain.review.service;

import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

public interface ReviewService {

    void write(ReviewCreate request);

    CustomPage<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO);

    ReviewDetailResponse findById(Long id);

    void edit(Long id, ReviewEdit reviewEdit);
}
