package teamproject.lam_server.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.entity.Review;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewListResponse {

    private Long id;
    private String writer;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate writeDate;

    public static ReviewListResponse of(Review review) {
        return ReviewListResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .writer(review.getMember().getNickname())
                .writeDate(review.getReviewDateTime().toLocalDate())
                .build();
    }
}
