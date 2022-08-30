package teamproject.lam_server.domain.comment.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleCommentReact;
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

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @ToString.Exclude
    private final List<ScheduleComment> children = new ArrayList<>();
    @OneToMany(mappedBy = "to")
    @ToString.Exclude
    private final Set<ScheduleCommentReact> reacts = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_comment_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    @ToString.Exclude
    private Schedule schedule;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    @ToString.Exclude
    private ScheduleComment parent;

    @Formula("(select count(sc.parent_comment_id) from schedule_comment sc where sc.parent_comment_id = schedule_comment_id)")
    private int childrenCount;
    @Formula("(select count(1) from schedule_comment_react scr where scr.to_schedule_comment_id = schedule_comment_id and scr.type = 'LIKE')")
    private int likeCount;
    @Formula("(select count(1) from schedule_comment_react scr where scr.to_schedule_comment_id = schedule_comment_id and scr.type = 'DISLIKE')")
    private int dislikeCount;

    @Builder
    public ScheduleComment(String content, Member member, Schedule schedule, @Nullable ScheduleComment parent) {
        this.content = content;
        setUpWriter(member);
        setUpSchedule(schedule);
        if (parent != null) {
            setUpParent(parent);
        }
    }

    protected void setUpWriter(Member member) {
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
