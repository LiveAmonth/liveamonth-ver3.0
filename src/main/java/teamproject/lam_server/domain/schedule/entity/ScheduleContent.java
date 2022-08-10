package teamproject.lam_server.domain.schedule.entity;


import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate date;

    private int cost;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    @ToString.Exclude
    private Schedule schedule;

    @Builder
    public ScheduleContent(String title, String content, LocalDate date, int cost, Schedule schedule) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.cost = cost;
        connectSchedule(schedule);
    }

    public void connectSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getScheduleContents().add(this);
    }
}
