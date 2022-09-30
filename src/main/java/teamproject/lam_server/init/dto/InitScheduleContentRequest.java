package teamproject.lam_server.init.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import teamproject.lam_server.global.dto.request.TimePeriodRequest;

import javax.validation.constraints.NotNull;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InitScheduleContentRequest {

    @NotNull
    private Long scheduleId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private TimePeriodRequest timePeriod;

    private int cost;
}
