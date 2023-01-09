package com.lam.liveamonthapp.domain.city.storage;

import com.lam.liveamonthapp.domain.city.dto.CityIntroDTO;
import com.lam.liveamonthapp.domain.city.dto.CityTransportDTO;
import com.lam.liveamonthapp.domain.city.dto.CityWeatherDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.lam.liveamonthapp.util.JsonUtil.jsonArrayToList;

@Getter
@Component
public class CityStorage {
    private static final String CITY_INTRO = "cityIntro";
    private static final String CITY_TRANSPORT = "cityTransport";
    private static final String CITY_WEATHER = "cityWeather";
    private final List<CityIntroDTO> introData;
    private final List<CityTransportDTO> transportData;
    private final List<CityWeatherDTO> weatherData;

    public CityStorage() {
        this.introData = jsonArrayToList(CITY_INTRO, CityIntroDTO.class);
        this.transportData = jsonArrayToList(CITY_TRANSPORT, CityTransportDTO.class);
        this.weatherData = jsonArrayToList(CITY_WEATHER, CityWeatherDTO.class);
    }
}
