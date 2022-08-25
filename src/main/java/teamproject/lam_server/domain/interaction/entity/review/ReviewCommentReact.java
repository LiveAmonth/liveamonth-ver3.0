package teamproject.lam_server.domain.interaction.entity.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCommentReact {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_review_comment_id")
    private ReviewComment to;

    @Enumerated(EnumType.STRING)
    private ReactType type;

    @Builder
    public ReviewCommentReact(Member from, ReviewComment to, ReactType type) {
        this.from = from;
        this.to = to;
        this.type = type;
        like();
    }

    private void like() {
        from.getReviewCommentReacts().add(this);
        to.getReacts().add(this);
    }

    public void cancelLike() {
        from.getReviewCommentReacts().remove(this);
        to.getReacts().remove(this);
    }
}
