package teamproject.lam_server.domain.schedule.entity;

import lombok.*;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.interaction.entity.ScheduleLike;
import teamproject.lam_server.domain.member.entity.Member;

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
public class Schedule {

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private final List<ScheduleContent> scheduleContents = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    @ToString.Exclude
    private final List<ScheduleReply> scheduleReplies = new ArrayList<>();
    @OneToMany(mappedBy = "to")
    @ToString.Exclude
    private final Set<ScheduleLike> likes = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    private String title;
    private CityName cityName;
    private int viewCount;
    private Boolean publicFlag;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @Builder
    public Schedule(String title, Boolean publicFlag, int viewCount, Member member, CityName cityName) {
        this.title = title;
        this.publicFlag = publicFlag;
        this.viewCount = viewCount;
        this.member = member;
        this.cityName = cityName;
    }
}
