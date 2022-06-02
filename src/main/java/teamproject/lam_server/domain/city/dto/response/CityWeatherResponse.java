package teamproject.lam_server.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityWeather;

@Getter
@Builder
public class CityWeatherResponse {
    private String month;
    private float maxDegree;
    private float minDegree;
    private float averageDegree;

    public static CityWeatherResponse of(CityWeather cityWeather) {
        return CityWeatherResponse.builder()
                .month(cityWeather.getMonth().getCode())
                .maxDegree(cityWeather.getMaxDegree())
                .minDegree(cityWeather.getMinDegree())
                .averageDegree(cityWeather.getAverageDegree())
                .build();
    }
}
