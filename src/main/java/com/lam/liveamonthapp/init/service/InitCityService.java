package com.lam.liveamonthapp.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.lam.liveamonthapp.domain.city.dto.request.CityIntroCreate;
import com.lam.liveamonthapp.domain.city.dto.request.CityTransportCreate;
import com.lam.liveamonthapp.domain.city.dto.request.CityWeatherCreate;
import com.lam.liveamonthapp.domain.city.repository.core.CityIntroRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityTransportRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityWeatherRepository;
import com.lam.liveamonthapp.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitCityService {
    private static final String CITY_INTRO = "cityIntro";
    private static final String CITY_TRANSPORT = "cityTransport";
    private static final String CITY_WEATHER = "cityWeather";
    private final CityIntroRepository cityIntroRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityWeatherRepository cityWeatherRepository;


    public void initCityIntroData() {
        cityIntroRepository.saveAll(
                JsonUtil.jsonArrayToList(CITY_INTRO, CityIntroCreate.class).stream()
                        .map(CityIntroCreate::toEntity)
                        .collect(Collectors.toList()
                        )
        );
    }

    public void initCityTransportData() {
        cityTransportRepository.saveAll(
                JsonUtil.jsonArrayToList(CITY_TRANSPORT, CityTransportCreate.class).stream()
                        .map(CityTransportCreate::toEntity)
                        .collect(Collectors.toList()
                        )
        );
    }

    public void initCityWeatherData() {
        cityWeatherRepository.saveAll(
                JsonUtil.jsonArrayToList(CITY_WEATHER, CityWeatherCreate.class).stream()
                        .map(CityWeatherCreate::toEntity)
                        .collect(Collectors.toList()
                        )
        );
    }
}
