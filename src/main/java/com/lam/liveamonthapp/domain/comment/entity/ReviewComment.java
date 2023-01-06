package com.lam.liveamonthapp.domain.comment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import com.lam.liveamonthapp.domain.interaction.entity.review.ReviewCommentInteraction;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.review.entity.Review;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "review_comment_id"))
public class ReviewComment extends CommentEntity {

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private final List<ReviewComment> children = new ArrayList<>();

    @OneToMany(mappedBy = "to", orphanRemoval = true)
    private final Set<ReviewCommentInteraction> interactions = new HashSet<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ReviewComment parent;

    @Basic(fetch= LAZY)
    @Formula("(select count(rc.parent_comment_id) from review_comment rc where rc.parent_comment_id = review_comment_id)")
    private int numberOfChildren;
    @Basic(fetch= LAZY)
    @Formula("(select count(1) from review_comment_interaction rcr where rcr.to_review_comment_id = review_comment_id and rcr.state = 'LIKE')")
    private int numberOfLikes;
    @Basic(fetch= LAZY)
    @Formula("(select count(1) from review_comment_interaction rcr where rcr.to_review_comment_id = review_comment_id and rcr.state = 'DISLIKE')")
    private int numberOfDislikes;


    @Builder
    public ReviewComment(String content, Member member, Review review, ReviewComment parent) {
        this.comment = content;
        setUpWriter(member);
        setUpReview(review);
        if (parent != null) {
            setUpParent(parent);
        }
    }

    protected void setUpWriter(Member member) {
        this.member = member;
        member.getReviewComments().add(this);
    }

    private void setUpReview(Review review) {
        this.review = review;
        review.getReviewComments().add(this);
    }

    private void setUpParent(ReviewComment reviewComment) {
        this.parent = reviewComment;
        reviewComment.children.add(this);
    }
}
