package teamproject.lam_server.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Followers {
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
    public Followers(Member from, Member to) {
        this.from = from;
        this.to = to;
        follow(from, to);
    }

    private void follow(Member from, Member to){
        from.getFollowing().add(this);
        to.getFollowers().add(this);
    }
    public void unFollow(Member from, Member to){
        from.getFollowing().remove(this);
        to.getFollowers().remove(this);
    }
}
