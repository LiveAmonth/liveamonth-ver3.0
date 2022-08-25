package teamproject.lam_server.domain.interaction.entity.review;

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
public class ReviewCommentDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_dislike_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_review_comment_id")
    private ReviewComment to;

    @Builder
    public ReviewCommentDislike(Member from, ReviewComment to) {
        this.from = from;
        this.to = to;
        dislike();
    }

    private void dislike() {
        from.getReviewCommentDislikes().add(this);
        to.getDislikes().add(this);
    }

    public void cancelDislike() {
        from.getReviewCommentDislikes().remove(this);
        to.getDislikes().remove(this);
    }
}
