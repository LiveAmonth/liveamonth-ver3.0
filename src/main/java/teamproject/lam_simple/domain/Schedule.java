package teamproject.lam_simple.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    private String title;

    @Column(name = "public_flag")
    private Boolean publicFlag;

    @Column(name = "view_count")
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleContent> scheduleContents = new ArrayList<>();

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleReply> scheduleReplies = new ArrayList<>();
}
