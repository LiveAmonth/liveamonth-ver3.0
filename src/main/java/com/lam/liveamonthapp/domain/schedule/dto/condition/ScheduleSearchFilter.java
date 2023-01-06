package com.lam.liveamonthapp.domain.schedule.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import com.lam.liveamonthapp.domain.city.constants.CityName;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class ScheduleSearchFilter {
    private CityName cityName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

}
