package com.lam.liveamonthapp.domain.interaction.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "followers_id"))
public class Follower extends BaseTimeEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    private Member from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_member_id")
    private Member to;

    @Builder
    public Follower(Member from, Member to) {
        this.from = from;
        this.to = to;
        follow();
    }

    private void follow() {
        from.getFollowing().add(this);
        to.getFollowers().add(this);
    }

    public void unFollow() {
        from.getFollowing().remove(this);
        to.getFollowers().remove(this);
    }
}
