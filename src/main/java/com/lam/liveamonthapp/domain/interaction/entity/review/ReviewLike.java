package com.lam.liveamonthapp.domain.interaction.entity.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.review.entity.Review;
import com.lam.liveamonthapp.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "review_like_id"))
public class ReviewLike extends BaseTimeEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_review_id")
    private Review to;


    @Builder
    public ReviewLike(Member from, Review to) {
        this.from = from;
        this.to = to;
        like();
    }

    private void like() {
        from.getReviewLikes().add(this);
        to.getLikes().add(this);
    }

    public void unFollow() {
        from.getReviewLikes().remove(this);
        to.getLikes().remove(this);
    }

}
