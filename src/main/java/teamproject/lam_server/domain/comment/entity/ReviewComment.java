package teamproject.lam_server.domain.comment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.interaction.entity.review.ReviewCommentReact;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.entity.Review;

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

    @OneToMany(mappedBy = "to")
    private final Set<ReviewCommentReact> reacts = new HashSet<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ReviewComment parent;

    @Formula("(select count(rc.parent_comment_id) from review_comment rc where rc.parent_comment_id = review_comment_id)")
    private int numberOfChildren;
    @Formula("(select count(1) from review_comment_react rcr where rcr.to_review_comment_id = review_comment_id and rcr.type = 'LIKE')")
    private int numberOfLikes;
    @Formula("(select count(1) from review_comment_react rcr where rcr.to_review_comment_id = review_comment_id and rcr.type = 'DISLIKE')")
    private int numberOfDislikes;


    @Builder
    public ReviewComment(String content, Member member, Review review, @Nullable ReviewComment parent) {
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
