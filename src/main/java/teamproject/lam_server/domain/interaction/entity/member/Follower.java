package teamproject.lam_server.domain.interaction.entity.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follower extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followers_id")
    private Long id;

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
