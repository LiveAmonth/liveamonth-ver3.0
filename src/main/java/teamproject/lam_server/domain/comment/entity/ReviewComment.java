package teamproject.lam_server.domain.comment.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.interaction.entity.ReviewCommentLike;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_id")
    private Long id;

    @Length(max = 1000)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    @ToString.Exclude
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private ReviewComment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @ToString.Exclude
    private final List<ReviewComment> children = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "to")
    private final Set<ReviewCommentLike> likes = new HashSet<>();

    @Formula("(select count(rc.parent) from review_comment rc where rx.parent = review_comment_id)")
    private int childrenCount;
    @Formula("(select count(1) from review_comment_like rcl where rcl.to_review_comment_id = review_comment_id)")
    private int likeCount;


    @Builder
    public ReviewComment(String content, Member member, @Nullable Review review, @Nullable ReviewComment parent) {
        this.content = content;
        setUpMember(member);
        if (review != null) setUpReview(review);
        if (parent != null) setUpParent(parent);
    }

    private void setUpMember(Member member) {
        this.member = member;
        member.getReviewComments().add(this);
    }

    private void setUpReview(Review review) {
        this.review = review;
        review.getReviewComments().add(this);
    }

    private void setUpParent(ReviewComment scheduleComment) {
        this.parent = scheduleComment;
        scheduleComment.children.add(this);
    }
}
