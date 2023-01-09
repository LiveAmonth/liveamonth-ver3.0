package com.lam.liveamonthapp.domain.city.service;

import com.lam.liveamonthapp.domain.city.dto.CityIntroDTO;
import com.lam.liveamonthapp.domain.city.dto.CityTransportDTO;
import com.lam.liveamonthapp.domain.city.dto.CityWeatherDTO;
import com.lam.liveamonthapp.domain.city.storage.CityStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.dto.response.CityGridDataResponse;
import com.lam.liveamonthapp.domain.city.dto.response.ExtraCityResponse;
import com.lam.liveamonthapp.domain.city.dto.response.ImageContentResponse;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityApiServiceImpl implements CityApiService {

    private final CityStorage storage;

    @Override
    public List<CityGridDataResponse> getCitySummaryInfo() {
        // 도시 이미지, 이름 가져오기
        Map<CityName, String> filteredIntro = getIntroContentsMap();
        // 도시 평균 기온 가져오기
        Map<CityName, Float> filteredWeather = getCityAverageTempMap();
        // 도시 교통 점수 가져오기
        Map<CityName, Integer> filteredTransport = getTransportsScoreMap();

        // CityGridResponse로 합치기
        return Arrays.stream(CityName.values())
                .map(cityName -> new CityGridDataResponse(
                        cityName,
                        filteredIntro.get(cityName),
                        filteredWeather.get(cityName),
                        filteredTransport.get(cityName)))
                .collect(Collectors.toList());
    }

    @Override
    public ExtraCityResponse searchTotalCityInfo(CityName cityName) {
        return ExtraCityResponse.of(storage.getTransportData(), storage.getWeatherData());
    }

    @Override
    public Map<CityIntroCategory, List<ImageContentResponse>> getCityIntro(CityName cityName) {
        return storage.getIntroData().stream().collect(Collectors.groupingBy(
                CityIntroDTO::getCategory,
                Collectors.mapping(ImageContentResponse::of, Collectors.toList())
        ));
    }

    private Map<CityName, String> getIntroContentsMap() {
        return storage.getIntroData().stream()
                .filter(intro -> intro.getCategory() == CityIntroCategory.INTRO)
                .collect(Collectors.toMap(
                        CityIntroDTO::getName,
                        CityIntroDTO::getImage
                ));
    }

    private Map<CityName, Float> getCityAverageTempMap() {
        return storage.getWeatherData().stream()
                .filter(weather -> weather.getMonth() == getCurrentMonth())
                .collect(Collectors.toMap(
                        CityWeatherDTO::getName,
                        CityWeatherDTO::getAvg
                ));
    }

    private Map<CityName, Integer> getTransportsScoreMap() {
        return storage.getTransportData().stream()
                .collect(Collectors.groupingBy(
                        CityTransportDTO::getName,
                        Collectors.summingInt(CityTransportDTO::getScore)));
    }

    private MonthCategory getCurrentMonth() {
        return MonthCategory.values()[LocalDateTime.now().getMonth().getValue() - 1];
    }
}
