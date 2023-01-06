package com.lam.liveamonthapp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.member.dto.response.SimpleProfileResponse;
import com.lam.liveamonthapp.global.dto.response.PeriodResponse;

@Getter
@AllArgsConstructor
public class ScheduleCardResponse {

    private Long id;
    private String title;
    private CityName city;
    private SimpleProfileResponse profile;
    private long cost;
    private long numberOfHits;
    private long numberOfLikes;
    private long numberOfComments;
    private PeriodResponse period;
    private boolean publicFlag;
}
