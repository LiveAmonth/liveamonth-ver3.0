package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.entity.CityTransport;
import teamproject.lam_server.app.city.entity.CityWeather;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TotalCityInfoResponse {

    //== 도시 소개 정보 ==//
    private String name;
    private String content;
    private String image;

    //== 도시 교통 정보 ==//
    private List<CityTransportResponse> transports = new ArrayList<>();

    //== 날씨 정보 ==//
    private List<CityWeatherResponse> weathers = new ArrayList<>();

    public TotalCityInfoResponse(CityInfo info, List<CityTransport> transports, List<CityWeather> weathers) {
        this.name = info.getName().getValue();
        this.content = info.getContent();
        this.image = info.getImage();

        this.transports = transports.stream().map(CityTransportResponse::new).collect(Collectors.toList());

        this.weathers = weathers.stream().map(CityWeatherResponse::new).collect(Collectors.toList());
    }
}
