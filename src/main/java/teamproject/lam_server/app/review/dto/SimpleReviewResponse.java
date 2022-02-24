package teamproject.lam_server.app.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import teamproject.lam_server.app.member.domain.Member;
import teamproject.lam_server.app.review.domain.Review;

@Data
@NoArgsConstructor
public class SimpleReviewResponse {

    private Member member;

    private String title;

    private String content;

    private int viewCount;

    public SimpleReviewResponse(Review review) {
        this.member = review.getMember();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.viewCount = review.getViewCount();
    }
}
