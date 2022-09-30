package teamproject.lam_server.domain.schedule.entity;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.global.entity.TimePeriod;

@Getter
public class ScheduleContentEditor {

    private final String title;

    private final String content;

    private final TimePeriod timePeriod;

    private final int cost;

    @Builder
    public ScheduleContentEditor(String title, String content, TimePeriod timePeriod, int cost) {
        this.title = title;
        this.content = content;
        this.timePeriod = timePeriod;
        this.cost = cost;
    }
}
