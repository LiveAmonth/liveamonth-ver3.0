package teamproject.lam_server.domain.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "review_tag_id"))
public class ReviewTag extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public ReviewTag(Tag tag) {
        this.tag = tag;
    }

    public static ReviewTag createReviewTag(Tag tag) {
        return ReviewTag.builder().tag(tag).build();
    }

    public void connectReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewTag)) return false;
        ReviewTag reviewTag = (ReviewTag) o;
        return Objects.equals(tag, reviewTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
