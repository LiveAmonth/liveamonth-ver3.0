package com.lam.liveamonthapp.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.global.dto.response.PeriodResponse;

@Getter
@AllArgsConstructor
public class EditableScheduleResponse {

    private Long id;
    private String title;
    private CityName city;
    private PeriodResponse period;
    private boolean publicFlag;
}
