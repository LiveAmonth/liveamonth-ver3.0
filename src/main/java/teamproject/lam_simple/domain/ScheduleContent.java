package teamproject.lam_simple.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "schedule_contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleContent {

    @Id
    @GeneratedValue
    @Column(name = "schedule_content_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @Column(name = "schedule_content_date")
    private Date scheduleContentDate;
    @Column(name = "cost_total")
    private int costTotal;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
