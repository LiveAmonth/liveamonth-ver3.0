package teamproject.lam_server.domain.review.service;

import org.springframework.data.domain.Page;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

public interface ReviewService {
    List<Review> findTOP5Reviews();

    PostIdResponse write(ReviewCreate request);

    Page<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO);

    ReviewDetailResponse findById(Long id);

    void edit(Long id, ReviewEdit reviewEdit);
}
