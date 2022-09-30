package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.city.dto.request.CityIntroCreate;
import teamproject.lam_server.domain.city.dto.request.CityTransportCreate;
import teamproject.lam_server.domain.city.dto.request.CityWeatherCreate;
import teamproject.lam_server.domain.city.repository.core.CityIntroRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.util.JsonUtil;

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
