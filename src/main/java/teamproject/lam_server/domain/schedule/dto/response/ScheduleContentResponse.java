package teamproject.lam_server.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.dto.response.TimePeriodResponse;

@Getter
@AllArgsConstructor
public class ScheduleContentResponse {

    private Long id;
    private String title;
    private String content;
    private long cost;
    private TimePeriodResponse timePeriod;
}
