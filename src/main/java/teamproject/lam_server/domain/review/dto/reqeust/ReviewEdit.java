package teamproject.lam_server.domain.review.dto.reqeust;

import lombok.*;
import teamproject.lam_server.domain.review.constants.ReviewCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEdit {

    @NotBlank
    private String title;

    @NotNull
    private ReviewCategory reviewCategory;

    @NotBlank
    private String content;

    @Builder
    public ReviewEdit(String title, ReviewCategory reviewCategory, String content) {
        this.title = title;
        this.reviewCategory = reviewCategory;
        this.content = content;
    }
}
