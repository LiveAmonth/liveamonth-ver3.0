package teamproject.lam_server.domain.schedule.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.BaseTimeEntity;
import teamproject.lam_server.global.entity.Period;

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
public class Schedule extends BaseTimeEntity {

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<ScheduleContent> scheduleContents = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "schedule")
    private final List<ScheduleComment> scheduleComments = new ArrayList<>();
    @ToString.Exclude
    @OneToMany(mappedBy = "to")
    private final Set<ScheduleLike> likes = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private CityName cityName;
    private int viewCount;
    private Boolean publicFlag;
    @Embedded
    private Period period;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Formula("(select count(1) from schedule_like sl where sl.to_schedule_id = schedule_id)")
    private int likeCount;
    @Formula("(select ifnull(sum(sc.cost),0) from schedule_content sc where sc.schedule_id = schedule_id)")
    private int totalCost;
    @Formula("(select count(1) from schedule_comment sc where sc.schedule_id = schedule_id and sc.parent_comment_id is null)")
    private int commentCount;

    @Builder
    public Schedule(String title, Boolean publicFlag, Member member, CityName cityName, Period period) {
        this.title = title;
        this.cityName = cityName;
        this.publicFlag = publicFlag;
        this.viewCount = 0;
        this.likeCount = 0;
        this.totalCost = 0;
        this.period = period;
        setUpMember(member);
    }

    private void setUpMember(Member member) {
        this.member = member;
        member.getSchedules().add(this);
    }

    public void makePrivate() {
        this.publicFlag = false;
    }

    public void makePublic() {
        this.publicFlag = true;
    }

    public void increaseTotalCost(int cost) {
        this.totalCost += cost;
    }

    public void decreaseTotalCost(int cost) {
        this.totalCost -= cost;
    }
}
