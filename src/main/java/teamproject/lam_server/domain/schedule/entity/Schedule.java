package teamproject.lam_server.domain.schedule.entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import teamproject.lam_server.domain.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    private String title;

    private Boolean publicFlag;

    private int viewCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    private String cityName;

    @JsonIgnore
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST)
    private final List<ScheduleContent> scheduleContents = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    @ToString.Exclude
    private final List<ScheduleReply> scheduleReplies = new ArrayList<>();

    @Builder
    public Schedule(String title, Boolean publicFlag, int viewCount, Member member, String cityName) {
        this.title = title;
        this.publicFlag = publicFlag;
        this.viewCount = viewCount;
        this.member = member;
        this.cityName = cityName;
    }
}
