package com.lam.liveamonthapp.domain.city.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.dto.response.api.CityGridDataResponse;
import com.lam.liveamonthapp.domain.city.dto.response.api.ExtraCityResponse;
import com.lam.liveamonthapp.domain.city.dto.response.api.ImageContentResponse;
import com.lam.liveamonthapp.domain.city.service.CityApiService;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;

import java.util.List;
import java.util.Map;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.READ_CITY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityApiController {
    private final CityApiService cityApiService;

    /**
     * dependence presentation layer::home(body)
     * -> slide info(top)
     */
    @GetMapping("/summary-info")
    public ResponseEntity<?> getCitySummaryInfo() {
        List<CityGridDataResponse> result = cityApiService.getCitySummaryInfo();
        return CustomResponse.success(result);
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<?> getCityIntro(@PathVariable CityName cityName) {
        Map<CityIntroCategory, List<ImageContentResponse>> result = cityApiService.getCity(cityName);
        return CustomResponse.success(READ_CITY, result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> total city info tab pane(top)
     */
    @GetMapping("/{cityName}/extra")
    public ResponseEntity<?> getExtraCityInfo(@PathVariable CityName cityName) {
        ExtraCityResponse result = cityApiService.searchTotalCityInfo(cityName);
        return CustomResponse.success(result);
    }
}
