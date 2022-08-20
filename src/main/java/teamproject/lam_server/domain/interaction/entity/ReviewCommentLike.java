package teamproject.lam_server.domain.interaction.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_review_comment_id")
    private ReviewComment to;

    @Builder
    public ReviewCommentLike(Member from, ReviewComment to) {
        this.from = from;
        this.to = to;
        like();
    }

    private void like() {
        from.getReviewCommentLikes().add(this);
        to.getLikes().add(this);
    }

    public void cancelLike() {
        from.getReviewCommentLikes().remove(this);
        to.getLikes().remove(this);
    }
}
