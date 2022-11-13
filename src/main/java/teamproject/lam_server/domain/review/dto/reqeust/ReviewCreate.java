package teamproject.lam_server.domain.review.dto.reqeust;

import lombok.*;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewTag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreate {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private String category;

    private Set<String> tags;

    @Builder
    public ReviewCreate(String title, String content, String category, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public Review toEntity(Member member, Set<ReviewTag> tags) {
        return Review.builder()
                .title(this.title)
                .content(this.content)
                .category(ReviewCategory.valueOf(this.category))
                .tags(tags)
                .member(member)
                .build();
    }
}
