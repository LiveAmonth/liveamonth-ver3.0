package teamproject.lam_server.domain.interaction.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_like_id")
    private Long id;

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
        follow();
    }

    private void follow() {
        from.getScheduleLikes().add(this);
        to.getLikes().add(this);
    }

    public void unFollow() {
        from.getScheduleLikes().remove(this);
        to.getLikes().remove(this);
    }
}
