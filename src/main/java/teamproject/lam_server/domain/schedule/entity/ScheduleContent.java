package teamproject.lam_server.domain.schedule.entity;


import lombok.*;
import teamproject.lam_server.global.entity.BaseTimeEntity;
import teamproject.lam_server.global.entity.TimePeriod;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ScheduleContent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_content_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @Embedded
    private TimePeriod timePeriod;

    private int cost;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Builder
    public ScheduleContent(String title, String content, TimePeriod timePeriod, int cost, Schedule schedule) {
        this.title = title;
        this.content = content;
        this.timePeriod = timePeriod;
        this.cost = cost;
        setUpSchedule(schedule);
    }

    public void setUpSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getScheduleContents().add(this);
    }
}
