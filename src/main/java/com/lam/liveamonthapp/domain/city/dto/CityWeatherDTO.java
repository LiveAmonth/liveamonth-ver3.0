package com.lam.liveamonthapp.domain.city.dto;

import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityWeatherDTO {

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
}
