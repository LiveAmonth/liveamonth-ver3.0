package teamproject.lam_server.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.dto.SimpleReviewResponse;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.global.dto.ProfileCardResponse;

import java.util.ArrayList;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.READ_REVIEW;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewApiController {
    private final MemberRepository memberRepository;

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
