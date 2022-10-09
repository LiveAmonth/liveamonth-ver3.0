package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.global.dto.response.TimePeriodResponse;

@Getter
@Builder
public class ScheduleContentResponse {

    private Long id;
    private String title;
    private String content;
    private long cost;
    private TimePeriodResponse timePeriod;

    public static ScheduleContentResponse of(ScheduleContent schedulecontent) {
        return ScheduleContentResponse.builder()
                .id(schedulecontent.getId())
                .title(schedulecontent.getTitle())
                .content(schedulecontent.getContent())
                .cost(schedulecontent.getCost())
                .timePeriod(TimePeriodResponse.of(schedulecontent.getTimePeriod()))
                .build();
    }

}
