package com.lam.liveamonthapp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.dto.response.TimePeriodResponse;

@Getter
@AllArgsConstructor
public class ScheduleContentResponse {

    private Long id;
    private String title;
    private String content;
    private long cost;
    private TimePeriodResponse timePeriod;
}
