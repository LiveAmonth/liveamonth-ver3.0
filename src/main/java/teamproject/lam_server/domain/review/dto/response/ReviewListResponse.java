package teamproject.lam_server.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.util.DateTimeUtil;

@Getter
@Builder
public class ReviewListResponse {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final String elapsedTime;
    private final Long viewCount;

    public static ReviewListResponse of(Review review) {
        return ReviewListResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .writer(review.getMember().getNickname())
                .content(review.getContent())
                .viewCount(review.getViewCount())
                .elapsedTime(DateTimeUtil.calcTimeBefore(review.getCreatedDate()))
                .build();
    }
}
