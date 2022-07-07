package teamproject.lam_server.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewDetailResponse {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private ReviewCategory reviewCategory;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeDateTime;
    private Long viewCount;
    // 회원 프로필, 댓글 정보..

    public static ReviewDetailResponse of(Review review) {
        return ReviewDetailResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .reviewCategory(review.getReviewCategory())
                .writer(review.getMember().getNickname())
                .writeDateTime(review.getReviewDateTime())
                .viewCount(review.getViewCount())
                .build();
    }
}
