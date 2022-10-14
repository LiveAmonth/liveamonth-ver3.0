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

    @PostMapping("/{loginId}")
    public ResponseEntity<?> writeReview(@PathVariable String loginId, @RequestBody @Valid ReviewCreate request) {
        PostIdResponse result = reviewService.write(loginId, request);
        return CustomResponse.success(CREATE_REVIEW, result);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getReview(@PathVariable Long id) {
        ReviewDetailResponse result = reviewService.getReview(id);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody @Valid ReviewEdit request) {
        reviewService.edit(id, request);
        return CustomResponse.success(UPDATE_REVIEW);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return CustomResponse.success(DELETE_REVIEW);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(ReviewSearchCond cond, PageableDTO pageableDTO) {
        CustomPage<ReviewListResponse> result = reviewService.search(cond, pageableDTO);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @GetMapping("/list/{loginId}")
    public ResponseEntity<?> getReviewByMember(@PathVariable String loginId,
                                            @RequestParam Integer size,
                                            @RequestParam(name = "last_id", required = false) Long lastId) {
        List<ReviewListResponse> result = reviewService.getReviewByMember(loginId, size, lastId);
        return CustomResponse.success(READ_REVIEW, result);
    }
}
