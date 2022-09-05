package teamproject.lam_server.init.dto;

import lombok.Getter;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.global.entity.TimePeriod;

import javax.validation.constraints.NotNull;

@Getter
public class InitScheduleContentRequest {

    @NotNull
    private Long scheduleId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private TimePeriod timePeriod;

    private int cost;

    public ScheduleContent toEntity(Schedule schedule) {
        return ScheduleContent.builder()
                .title(title)
                .content(content)
                .timePeriod(timePeriod)
                .cost(cost)
                .schedule(schedule)
                .build();
    }
}
