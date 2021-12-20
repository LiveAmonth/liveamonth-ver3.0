package teamproject.lam_simple.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "schedule_replies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleReply {

    @Id
    @GeneratedValue
    @Column(name = "schedule_reply_id")
    private Long id;

    @Lob
    private String content;

    @Column(name = "schedule_reply_date")
    private LocalDateTime scheduleReplyDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_schedule_reply_id")
    private ScheduleReply superScheduleReply;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "superScheduleReply", cascade = CascadeType.ALL)
    private List<ScheduleReply> subScheduleReplies;
}
