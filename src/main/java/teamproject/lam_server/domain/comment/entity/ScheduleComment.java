package teamproject.lam_server.domain.comment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AttributeOverride(name = "id", column = @Column(name = "schedule_comment_id"))
public class ScheduleComment extends CommentEntity {

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private final List<ScheduleComment> children = new ArrayList<>();
    @OneToMany(mappedBy = "to", orphanRemoval = true)
    private final Set<ScheduleCommentReact> reacts = new HashSet<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ScheduleComment parent;

    @Formula("(select count(sc.parent_comment_id) from schedule_comment sc where sc.parent_comment_id = schedule_comment_id)")
    private int numberOfChildren;
    @Formula("(select count(1) from schedule_comment_react scr where scr.to_schedule_comment_id = schedule_comment_id and scr.type = 'LIKE')")
    private int numberOfLikes;
    @Formula("(select count(1) from schedule_comment_react scr where scr.to_schedule_comment_id = schedule_comment_id and scr.type = 'DISLIKE')")
    private int numberOfDislikes;

    @Builder
    public ScheduleComment(String content, Member member, Schedule schedule, @Nullable ScheduleComment parent) {
        this.comment = content;
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
