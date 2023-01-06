package com.lam.liveamonthapp.domain.city.dto.request;

import lombok.*;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.entity.CityWeather;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityWeatherCreate {

    @NotNull
    private CityName name;
    @NotNull
    private MonthCategory month;
    @NotNull
    private float avg;
    @NotNull
    private float min;
    @NotNull
    private float max;

    public CityWeather toEntity() {
        return CityWeather.builder()
                .name(name)
                .month(month)
                .averageDegree(avg)
                .maxDegree(max)
                .minDegree(min)
                .build();
    }
}
