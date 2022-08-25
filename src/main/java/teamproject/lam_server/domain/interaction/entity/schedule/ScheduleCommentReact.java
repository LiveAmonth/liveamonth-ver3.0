package teamproject.lam_server.domain.interaction.entity.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCommentReact {

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

    @Enumerated(STRING)
    private ReactType type;

    @Builder
    public ScheduleCommentReact(Member from, ScheduleComment to, ReactType type) {
        this.from = from;
        this.to = to;
        this.type = type;
        like();
    }

    private void like() {
        from.getScheduleCommentReacts().add(this);
        to.getReacts().add(this);
    }

    public void cancelLike() {
        from.getScheduleCommentReacts().remove(this);
        to.getReacts().remove(this);
    }
}
