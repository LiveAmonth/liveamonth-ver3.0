package teamproject.lam_server.domain.comment.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.interaction.entity.ScheduleCommentLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;

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
public class ScheduleComment extends CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_comment_id")
    private Long id;

    @Length(max = 1000)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    @ToString.Exclude
    private Schedule schedule;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    private ScheduleComment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @ToString.Exclude
    private final List<ScheduleComment> children = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "to")
    private final Set<ScheduleCommentLike> likes = new HashSet<>();

    @Formula("(select count(sc.parent) from schedule_comment sc where sc.parent = schedule_comment_id)")
    private int childrenCount;
    @Formula("(select count(1) from schedule_comment_like scl where scl.to_schedule_comment_id = schedule_comment_id)")
    private int likeCount;

    @Builder
    public ScheduleComment(String content, Member member, @Nullable Schedule schedule, @Nullable ScheduleComment parent) {
        this.content = content;
        setUpMember(member);
        if (parent != null) setUpParent(parent);
        if (schedule != null) setUpSchedule(schedule);
    }

    private void setUpMember(Member member) {
        this.member = member;
        member.getScheduleComments().add(this);
    }

    private void setUpSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getScheduleComments().add(this);
    }

    private void setUpParent(ScheduleComment scheduleComment) {
        this.parent = scheduleComment;
        scheduleComment.children.add(this);
    }
}
