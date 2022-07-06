package teamproject.lam_server.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.dto.response.SimpleReviewResponse;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.service.ReviewService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.global.dto.ProfileCardResponse;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Slf4j
public class ReviewApiController {
    private final MemberRepository memberRepository;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> writeReview(@RequestBody @Valid ReviewCreate request) {
        PostIdResponse result = reviewService.write(request);
        return CustomResponse.success(CREATE_REVIEW, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        ReviewDetailResponse result = reviewService.findById(id);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody @Valid ReviewEdit request) {
        log.info("request={}", request.getTitle());
        log.info("request={}", request.getReviewCategory());
        log.info("request={}", request.getContent());
        reviewService.edit(id, request);
        return CustomResponse.success(UPDATE_REVIEW);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody(required = false) ReviewSearchCond cond, PageableDTO pageableDTO) {
        if (cond == null) cond = new ReviewSearchCond();
        Page<ReviewListResponse> result = reviewService.search(cond, pageableDTO);
        return CustomResponse.success(READ_REVIEW, result);
    }

    @GetMapping("/other")
    public ResponseEntity<?> getOtherReviews() {
        return CustomResponse.success(READ_REVIEW, testData());
    }

    private List<SimpleReviewResponse> testData() {
        Member member = memberRepository.findByLoginId("rbdus7174").orElse(null);
        List<SimpleReviewResponse> result = new ArrayList<>();
        for (int i = 1; i < 6 + 1; i++) {
            SimpleReviewResponse response = new SimpleReviewResponse();
            response.setContent(i + "번 게시글 미리 보기 내용입니다.");
            response.setTitle(i + "번 게시글");
            response.setMember(ProfileCardResponse.of(member));
            response.setViewCount(i * 100);
            result.add(response);
        }
        return result;
    }
}
