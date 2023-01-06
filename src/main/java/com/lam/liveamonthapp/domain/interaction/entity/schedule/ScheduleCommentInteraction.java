package com.lam.liveamonthapp.domain.interaction.entity.schedule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.comment.entity.ScheduleComment;
import com.lam.liveamonthapp.domain.interaction.entity.InteractionEntity;
import com.lam.liveamonthapp.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "schedule_comment_interaction_id"))
public class ScheduleCommentInteraction extends InteractionEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_schedule_comment_id")
    private ScheduleComment to;

    @Override
    public Long getCommentId() {
        return to.getId();
    }
}
