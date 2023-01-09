package com.lam.liveamonthapp.domain.city.service;

import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.dto.response.CityGridDataResponse;
import com.lam.liveamonthapp.domain.city.dto.response.ExtraCityResponse;
import com.lam.liveamonthapp.domain.city.dto.response.ImageContentResponse;

import java.util.List;
import java.util.Map;

public interface CityApiService {

    List<CityGridDataResponse> getCitySummaryInfo();

    ExtraCityResponse searchTotalCityInfo(CityName cityName);

    Map<CityIntroCategory, List<ImageContentResponse>> getCityIntro(CityName cityName);
}
