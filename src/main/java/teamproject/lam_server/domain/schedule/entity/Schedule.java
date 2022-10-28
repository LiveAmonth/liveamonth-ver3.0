package teamproject.lam_server.domain.schedule.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.global.entity.BaseEntity;
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
@AttributeOverride(name = "id", column = @Column(name = "schedule_id"))
public class Schedule extends BaseEntity {

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<ScheduleContent> scheduleContents = new ArrayList<>();
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<ScheduleComment> scheduleComments = new ArrayList<>();
    @OneToMany(mappedBy = "to", orphanRemoval = true)
    private final Set<ScheduleLike> likes = new HashSet<>();
    private String title;
    @Enumerated(EnumType.STRING)
    private CityName cityName;
    private long numberOfHits;
    private Boolean publicFlag;
    @Embedded
    private Period period;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Formula("(select count(1) from schedule_like sl where sl.to_schedule_id = schedule_id)")
    private long numberOfLikes;

    @Formula("(select ifnull(sum(sc.cost),0) from schedule_content sc where sc.schedule_id = schedule_id)")
    private long totalCost;

    @Basic(fetch = LAZY)
    @Formula("(select count(1) from schedule_comment sc where sc.schedule_id = schedule_id and sc.parent_comment_id is null)")
    private long numberOfComments;

    @Builder
    public Schedule(String title, Boolean publicFlag, Member member, CityName cityName, Period period) {
        this.title = title;
        this.cityName = cityName;
        this.publicFlag = publicFlag;
        this.numberOfHits = 0;
        this.period = period;
        setUpMember(member);
    }

    private void setUpMember(Member member) {
        this.member = member;
        member.getSchedules().add(this);
    }

    /**
     * Editor
     */
    public ScheduleEditor.ScheduleEditorBuilder toEditor() {
        return ScheduleEditor.builder()
                .title(title)
                .city(cityName)
                .publicFlag(publicFlag)
                .period(period);
    }

    public void edit(ScheduleEditor editor) {
        title = editor.getTitle();
        cityName = editor.getCity();
        publicFlag = editor.isPublicFlag();
        period = editor.getPeriod();
    }
}
