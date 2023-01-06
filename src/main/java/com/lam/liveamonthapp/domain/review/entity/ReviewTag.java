package com.lam.liveamonthapp.domain.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.global.entity.BaseTimeEntity;

import javax.persistence.*;

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
}
