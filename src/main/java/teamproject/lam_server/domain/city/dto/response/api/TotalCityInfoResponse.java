package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.dto.response.CityTransportResponse;
import teamproject.lam_server.domain.city.dto.response.CityWeatherResponse;
import teamproject.lam_server.domain.city.entity.CityIntro;
import teamproject.lam_server.domain.city.entity.CityTransport;
import teamproject.lam_server.domain.city.entity.CityWeather;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TotalCityInfoResponse {

    //== 도시 소개 정보 ==//
    private String cityName;
    private String content;
    private String image;

    //== 도시 교통 정보 ==//
    private List<CityTransportResponse> transports;

    //== 날씨 정보 ==//
    private List<CityWeatherResponse> weathers;

    public static TotalCityInfoResponse of(CityIntro info, List<CityTransport> transports, List<CityWeather> weathers) {
        return TotalCityInfoResponse.builder()
                .cityName(info.getName().name())
                .content(info.getContent())
                .image(info.getImage())
                .transports(transports.stream().map(CityTransportResponse::of).collect(Collectors.toList()))
                .weathers(weathers.stream().map(CityWeatherResponse::of).collect(Collectors.toList()))
                .build();
    }
}
