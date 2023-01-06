package com.lam.liveamonthapp.domain.schedule.dto.condition;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.lam.liveamonthapp.domain.city.constants.CityName;

import java.time.LocalDate;


@Getter
@Setter
@Builder
public class ScheduleSearchCond{

    private String memberNickname;

    private String title;

    private CityName cityName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
}
