package teamproject.lam_server.domain.review.entity.editor;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.review.constants.ReviewCategory;

@Getter
public class ReviewEditor {

    private final String title;
    private final String content;
    private final ReviewCategory reviewCategory;

    @Builder
    public ReviewEditor(String title, String content, ReviewCategory reviewCategory) {
        this.title = title;
        this.content = content;
        this.reviewCategory = reviewCategory;
    }
}
