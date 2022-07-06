package teamproject.lam_server.domain.review.service;

import org.springframework.data.domain.Page;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.WriteReviewRequest;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

public interface ReviewService {
    List<Review> findTOP5Reviews();

    PostIdResponse write(WriteReviewRequest request);

    Page<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO);

    ReviewDetailResponse findById(Long id);
}
