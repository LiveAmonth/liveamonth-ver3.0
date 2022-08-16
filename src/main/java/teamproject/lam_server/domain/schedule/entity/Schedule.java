package teamproject.lam_server.domain.schedule.entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.interaction.entity.ScheduleLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.Period;
import teamproject.lam_server.global.entity.BaseTimeEntity;

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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private final List<ScheduleContent> scheduleContents = new ArrayList<>();
    @OneToMany(mappedBy = "schedule")
    @ToString.Exclude
    @JsonIgnore
    private final List<ScheduleReply> scheduleReplies = new ArrayList<>();
    @OneToMany(mappedBy = "to")
    @ToString.Exclude
    @JsonIgnore
    private final Set<ScheduleLike> likes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private CityName cityName;

    private int viewCount;

    private int likeCount;

    private int totalCost;

    private Boolean publicFlag;

    @Embedded
    private Period period;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @Builder
    public Schedule(String title, Boolean publicFlag, Member member, CityName cityName, Period period) {
        this.title = title;
        this.cityName = cityName;
        this.publicFlag = publicFlag;
        this.viewCount = 0;
        this.likeCount = 0;
        this.totalCost = 0;
        this.period = period;
        connectMember(member);
    }

    private void connectMember(Member member) {
        this.member = member;
        member.getSchedules().add(this);
    }

    public void makePrivate() {
        this.publicFlag = false;
    }

    public void makePublic() {
        this.publicFlag = true;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseTotalCost(int cost) {
        this.totalCost += cost;
    }
    public void decreaseTotalCost(int cost) {
        this.totalCost -= cost;
    }
}
