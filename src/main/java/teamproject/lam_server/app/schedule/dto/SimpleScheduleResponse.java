package teamproject.lam_server.app.schedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import teamproject.lam_server.app.review.domain.Review;
import teamproject.lam_server.global.dto.ProfileCardResponse;

@Data
@NoArgsConstructor
public class SimpleScheduleResponse {

    private ProfileCardResponse member;

    private String title;

    private String content;

    private int viewCount;

    public SimpleScheduleResponse(Review review) {
        this.member = new ProfileCardResponse(review.getMember());
        this.title = review.getTitle();
        this.content = review.getContent();
        this.viewCount = review.getViewCount();
    }
}
