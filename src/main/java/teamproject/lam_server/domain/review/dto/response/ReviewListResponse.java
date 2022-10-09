package teamproject.lam_server.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.util.DateTimeUtil;

import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class ReviewListResponse {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private String elapsedTime;
    private String numberOfHits;

    public static ReviewListResponse of(Review review) {
        return ReviewListResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .writer(review.getMember().getNickname())
                .content(review.getContent())
                .numberOfHits(countFormat(review.getNumberOfHits()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(review.getCreatedDate()))
                .build();
    }
}
