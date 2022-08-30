package teamproject.lam_server.domain.interaction.entity.review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.interaction.entity.schedule.ReactEntity;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCommentReact extends ReactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_review_comment_id")
    private ReviewComment to;
}
