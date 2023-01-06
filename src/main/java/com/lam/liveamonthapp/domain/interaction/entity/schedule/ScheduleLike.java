package com.lam.liveamonthapp.domain.interaction.entity.schedule;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "schedule_like_id"))
public class ScheduleLike extends BaseTimeEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_schedule_id")
    private Schedule to;


    @Builder
    public ScheduleLike(Member from, Schedule to) {
        this.from = from;
        this.to = to;
        like();
    }

    private void like() {
        from.getScheduleLikes().add(this);
        to.getLikes().add(this);
    }

    public void cancelLike() {
        from.getScheduleLikes().remove(this);
        to.getLikes().remove(this);
    }
}
