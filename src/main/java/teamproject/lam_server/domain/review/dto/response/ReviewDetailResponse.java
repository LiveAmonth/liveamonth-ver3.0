package teamproject.lam_server.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewDetailResponse {
    private Long id;
    private String title;
    private SimpleProfileResponse profile;
    private String content;
    private ReviewCategory category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd a h:mm", timezone = "Asia/Seoul")
    private LocalDateTime createDateTime;
    private long numberOfHits;
    private long numberOfComments;
    private long numberOfLikes;

    public static ReviewDetailResponse of(Review review) {
        return ReviewDetailResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .profile(SimpleProfileResponse.of(review.getMember()))
                .content(review.getContent())
                .category(review.getCategory())
                .createDateTime(review.getCreatedDate())
                .numberOfHits(review.getNumberOfHits())
                .numberOfLikes(review.getNumberOfLikes())
                .numberOfComments(review.getNumberOfComments())
                .build();
    }
}
