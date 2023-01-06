package com.lam.liveamonthapp.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.TransportCategory;
import com.lam.liveamonthapp.domain.city.entity.CityTransport;

@Getter
@Builder
public class CityTransportResponse {
    private Long id;
    private CityName name;
    private TransportCategory category;
    private int stationCount;
    private int score;

    public static CityTransportResponse of(CityTransport cityTransport) {
        return CityTransportResponse.builder()
                .id(cityTransport.getId())
                .name(cityTransport.getName())
                .category(cityTransport.getCityTransportCat())
                .stationCount(cityTransport.getStationCount())
                .score(cityTransport.getScore())
                .build();
    }
}
