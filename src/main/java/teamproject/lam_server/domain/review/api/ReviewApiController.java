package teamproject.lam_server.domain.review.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.global.dto.OtherContentSearch;
import teamproject.lam_server.domain.review.dto.SimpleReviewResponse;
import teamproject.lam_server.domain.review.service.ReviewService;
import teamproject.lam_server.global.dto.ProfileCardResponse;
import teamproject.lam_server.global.dto.CustomResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@Slf4j
public class ReviewApiController {
    private final CustomResponse customResponse;

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    @PostMapping("/other")
    public ResponseEntity<?> getOtherReviews(@RequestBody @Valid OtherContentSearch search) {
        log.info("orderBy={}", search.getOrderBy());
        return customResponse.success(testData(search));

    }

    private List<SimpleReviewResponse> testData(OtherContentSearch search){
        Member member = memberRepository.findByLoginId("rbdus7174").orElse(null);
        List<SimpleReviewResponse> result = new ArrayList<>();
        for (int i = 1; i < search.getLimit()+1; i++) {
            SimpleReviewResponse response = new SimpleReviewResponse();
            response.setContent(i+"번 게시글 미리 보기 내용입니다.");
            response.setTitle(i+"번 게시글");
            response.setMember(new ProfileCardResponse(member));
            response.setViewCount(i*100);
            result.add(response);
        }
        return result;
    }
}
