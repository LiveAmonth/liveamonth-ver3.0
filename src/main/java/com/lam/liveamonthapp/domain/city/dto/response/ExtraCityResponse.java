package com.lam.liveamonthapp.domain.city.dto.response;

import com.lam.liveamonthapp.domain.city.dto.CityTransportDTO;
import com.lam.liveamonthapp.domain.city.dto.CityWeatherDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExtraCityResponse {

    //== 도시 교통 정보 ==//
    private List<CityTransportDTO> transports;

    //== 날씨 정보 ==//
    private List<CityWeatherDTO> weathers;

    public static ExtraCityResponse of(List<CityTransportDTO> transports, List<CityWeatherDTO> weathers) {
        return ExtraCityResponse.builder()
                .transports(transports)
                .weathers(weathers)
                .build();
    }
}
