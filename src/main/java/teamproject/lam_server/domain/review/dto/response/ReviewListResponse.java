package teamproject.lam_server.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.util.DateTimeUtil;

@Getter
@Builder
public class ReviewListResponse {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private String elapsedTime;
    private long numberOfHits;
    private long numberOfComments;
    private long numberOfLikes;

    public static ReviewListResponse of(Review review) {
        return ReviewListResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .writer(review.getMember().getNickname())
                .content(review.getContent())
                .numberOfHits(review.getNumberOfHits())
                .numberOfComments(review.getNumberOfComments())
                .numberOfLikes(review.getNumberOfLikes())
                .elapsedTime(DateTimeUtil.calcTimeBefore(review.getCreatedDate()))
                .build();
    }
}
