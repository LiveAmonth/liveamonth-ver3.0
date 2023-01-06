package com.lam.liveamonthapp.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.entity.CityWeather;

@Getter
@Builder
public class CityWeatherResponse {
    private Long id;
    private CityName name;
    private MonthCategory month;
    private float maxDegree;
    private float minDegree;
    private float averageDegree;

    public static CityWeatherResponse of(CityWeather cityWeather) {
        return CityWeatherResponse.builder()
                .id(cityWeather.getId())
                .name(cityWeather.getName())
                .month(cityWeather.getMonth())
                .maxDegree(cityWeather.getMaxDegree())
                .minDegree(cityWeather.getMinDegree())
                .averageDegree(cityWeather.getAverageDegree())
                .build();
    }
}
