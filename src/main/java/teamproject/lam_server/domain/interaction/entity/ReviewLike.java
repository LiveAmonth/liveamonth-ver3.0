package teamproject.lam_server.domain.interaction.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_like_id")
    private Long id;
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
