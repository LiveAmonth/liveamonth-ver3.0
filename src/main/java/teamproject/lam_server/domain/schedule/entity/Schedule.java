package teamproject.lam_server.domain.schedule.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "schedules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    private String title;

    @Column(name = "public_flag")
    private Boolean publicFlag;

    @Column(name = "view_count")
    private int viewCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CityName cityName;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleContent> scheduleContents = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleReply> scheduleReplies = new ArrayList<>();
}
