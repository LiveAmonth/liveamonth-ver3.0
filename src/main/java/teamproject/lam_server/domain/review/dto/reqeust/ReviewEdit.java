package teamproject.lam_server.domain.review.dto.reqeust;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.review.constants.ReviewCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEdit {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private ReviewCategory category;

    private Set<String> tags;
}
