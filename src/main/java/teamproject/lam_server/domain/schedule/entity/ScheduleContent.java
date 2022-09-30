package teamproject.lam_server.domain.schedule.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.global.entity.BaseEntity;
import teamproject.lam_server.global.entity.TimePeriod;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "schedule_content_id"))
public class ScheduleContent extends BaseEntity {

    private String title;

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

    /**
     * 연관관계 매핑
     */
    private void setUpSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getScheduleContents().add(this);
    }

    /**
     * Editor
     */
    public ScheduleContentEditor.ScheduleContentEditorBuilder toEditor() {
        return ScheduleContentEditor.builder()
                .title(title)
                .content(content)
                .timePeriod(timePeriod)
                .cost(cost);
    }

    public void toEdit(ScheduleContentEditor editor) {
        title = editor.getTitle();
        content = editor.getContent();
        timePeriod = editor.getTimePeriod();
        cost = editor.getCost();
    }
}
