package com.lam.liveamonthapp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.global.dto.response.PeriodResponse;

@Getter
@AllArgsConstructor
public class MyScheduleResponse {

    private Long id;
    private String title;
    private CityName city;
    private long cost;
    private PeriodResponse period;
    private boolean publicFlag;
    private long numberOfHits;
    private long numberOfLikes;
    private long numberOfComments;
}
