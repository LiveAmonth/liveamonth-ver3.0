package teamproject.lam_server.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.Review;

import static teamproject.lam_server.util.DateTimeUtil.calcTimeBefore;
import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class ReviewDetailResponse {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private ReviewCategory category;
    private String elapsedTime;
    private String viewCount;
    // 회원 프로필, 댓글 정보..

    public static ReviewDetailResponse of(Review review) {
        return ReviewDetailResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .category(review.getCategory())
                .writer(review.getMember().getNickname())
                .elapsedTime(calcTimeBefore(review.getCreatedDate()))
                .viewCount(countFormat(review.getViewCount()))
                .build();
    }
}
