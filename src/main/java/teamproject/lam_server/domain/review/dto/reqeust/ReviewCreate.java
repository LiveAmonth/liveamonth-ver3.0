package teamproject.lam_server.domain.review.dto.reqeust;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewTag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreate {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private ReviewCategory category;

    private Set<String> tags;

    public Review toEntity(Member member, Set<ReviewTag> tags) {
        return Review.builder()
                .title(this.title)
                .content(this.content)
                .category(this.category)
                .tags(tags)
                .member(member)
                .build();
    }
}
