package com.lam.liveamonthapp.domain.city.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.dto.response.api.CityGridDataResponse;
import com.lam.liveamonthapp.domain.city.dto.response.api.ExtraCityResponse;
import com.lam.liveamonthapp.domain.city.dto.response.api.ImageContentResponse;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;
import com.lam.liveamonthapp.domain.city.repository.core.CityIntroRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityTransportRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityWeatherRepository;
import com.lam.liveamonthapp.domain.city.repository.query.CityQueryRepository;

import java.time.LocalDateTime;
import java.util.*;

import static com.lam.liveamonthapp.domain.city.constants.CityIntroCategory.INTRO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityApiServiceImpl implements CityApiService {
    private final CityIntroRepository cityIntroRepository;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityQueryRepository cityQueryRepository;

    @Override
    public List<CityGridDataResponse> getCitySummaryInfo() {
        return cityQueryRepository.findCityGridInfo(INTRO, getCurrentMonth());
    }

    @Override
    public ExtraCityResponse searchTotalCityInfo(CityName cityName) {
        return ExtraCityResponse.of(
                cityTransportRepository.findByName(cityName),
                cityWeatherRepository.findByName(cityName));
    }

    @Override
    public Map<CityIntroCategory, List<ImageContentResponse>> getCity(CityName cityName) {
        List<CityIntro> cityIntros = cityIntroRepository.findByName(cityName);
        Map<CityIntroCategory, List<ImageContentResponse>> introMap = new HashMap<>();
        for (CityIntro cityIntro : cityIntros) {
            CityIntroCategory category = cityIntro.getCityInfoCat();
            if (introMap.containsKey(cityIntro.getCityInfoCat()) && category != INTRO){
                introMap.get(category).add(ImageContentResponse.of(cityIntro));
            }else {
                introMap.put(category, new ArrayList<>(Collections.singletonList(ImageContentResponse.of(cityIntro))));
            }

        }
        return introMap;
    }

    private MonthCategory getCurrentMonth() {
        return MonthCategory.values()[LocalDateTime.now().getMonth().getValue() - 1];
    }
}
