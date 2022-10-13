package teamproject.lam_server.domain.review.entity;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.constants.ReviewCategory;

import java.util.Set;

@Getter
public class ReviewEditor {

    private final String title;
    private final String content;
    private final ReviewCategory category;
    private final Set<ReviewTag> tags;

    @Builder
    public ReviewEditor(String title, String content, ReviewCategory category, Set<ReviewTag> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }
}
