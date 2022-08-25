package teamproject.lam_server.domain.interaction.entity.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_comment_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_schedule_comment_id")
    private ScheduleComment to;

    @Builder
    public ScheduleCommentLike(Member from, ScheduleComment to) {
        this.from = from;
        this.to = to;
        like();
    }

    private void like() {
        from.getScheduleCommentLikes().add(this);
        to.getLikes().add(this);
    }

    public void cancelLike() {
        from.getScheduleCommentLikes().remove(this);
        to.getLikes().remove(this);
    }
}
