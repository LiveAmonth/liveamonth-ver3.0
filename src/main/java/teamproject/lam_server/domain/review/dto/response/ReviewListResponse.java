package teamproject.lam_server.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.entity.Review;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewListResponse {

    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate writeDate;
    private final Long viewCount;

    public static ReviewListResponse of(Review review) {
        return ReviewListResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .writer(review.getMember().getNickname())
                .content(review.getContent())
                .writeDate(review.getDateTime().toLocalDate())
                .viewCount(review.getViewCount())
                .build();
    }
}
