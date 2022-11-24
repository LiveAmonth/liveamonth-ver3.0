package teamproject.lam_server.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.dto.response.TagResponse;
import teamproject.lam_server.domain.review.service.ReviewService;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Slf4j
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/{login_id}")
    public ResponseEntity<?> writeReview(
            @PathVariable("login_id") String loginId,
            @RequestBody @Valid ReviewCreate request) {
        PostIdResponse result = reviewService.write(loginId, request);
        return CustomResponse.success(CREATE_REVIEW, result);
    }

    @PatchMapping("/{review_id}")
    public ResponseEntity<?> edit(
            @PathVariable("review_id") Long reviewId,
            @RequestBody @Valid ReviewEdit request) {
        reviewService.edit(reviewId, request);
        return CustomResponse.success(UPDATE_REVIEW);
    }

    @DeleteMapping("/{review_id}")
    public ResponseEntity<?> delete(@PathVariable("review_id") Long reviewId) {
        reviewService.delete(reviewId);
        return CustomResponse.success(DELETE_REVIEW);
    }

    @GetMapping("/{review_id}/detail")
    public ResponseEntity<?> getReview(@PathVariable("review_id") Long reviewId) {
        ReviewDetailResponse result = reviewService.getReview(reviewId);
        log.info("result={}", result);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(ReviewSearchCond cond, PageableDTO pageableDTO) {
        CustomPage<ReviewListResponse> result = reviewService.search(cond, pageableDTO);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @GetMapping("/list/{login_id}")
    public ResponseEntity<?> getReviewByMember(@PathVariable("login_id") String loginId,
                                               @RequestParam Integer size,
                                               @RequestParam(name = "last_id", required = false) Long lastId) {
        List<ReviewListResponse> result = reviewService.getReviewByMember(loginId, size, lastId);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @PatchMapping("/{review_id}/count-up")
    public ResponseEntity<?> viewCountUp(@PathVariable("review_id") Long reviewId) {
        reviewService.viewCountUp(reviewId);
        return CustomResponse.success(READ_REVIEW);
    }

    @GetMapping("/recommendation-tags")
    public ResponseEntity<?> getRecommendationTags() {
        List<TagResponse> result = reviewService.getRecommendationTags();
        return CustomResponse.success(READ_TAG, result);
    }
}
