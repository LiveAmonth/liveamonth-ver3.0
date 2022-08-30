package teamproject.lam_server.domain.comment.entity;

import lombok.*;
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
@ToString
public class ReviewComment extends CommentEntity {

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @ToString.Exclude
    private final List<ReviewComment> children = new ArrayList<>();

    @OneToMany(mappedBy = "to")
    @ToString.Exclude
    private final Set<ReviewCommentReact> reacts = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    @ToString.Exclude
    private Review review;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    @ToString.Exclude
    private ReviewComment parent;

    @Formula("(select count(rc.parent_comment_id) from review_comment rc where rc.parent_comment_id = review_comment_id)")
    private int childrenCount;
    @Formula("(select count(1) from review_comment_react rcr where rcr.to_review_comment_id = review_comment_id and rcr.type = 'LIKE')")
    private int likeCount;
    @Formula("(select count(1) from review_comment_react rcr where rcr.to_review_comment_id = review_comment_id and rcr.type = 'DISLIKE')")
    private int dislikeCount;


    @Builder
    public ReviewComment(String content, Member member, Review review, @Nullable ReviewComment parent) {
        this.content = content;
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
