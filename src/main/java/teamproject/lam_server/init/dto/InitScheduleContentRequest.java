package teamproject.lam_server.init.dto;

import lombok.Getter;
import teamproject.lam_server.global.dto.request.TimePeriodRequest;

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
    private TimePeriodRequest timePeriod;

    private int cost;
}
