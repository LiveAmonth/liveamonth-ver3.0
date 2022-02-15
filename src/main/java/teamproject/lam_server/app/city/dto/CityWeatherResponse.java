package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityWeather;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityName;

@Data
public class CityWeatherResponse {
    private Long id;
    private CityName name;
    private String month;
    private float maxDegree;
    private float minDegree;
    private float averageDegree;

    public CityWeatherResponse(CityWeather cityWeather) {
        this.id = cityWeather.getId();
        this.name = cityWeather.getName();
        this.month = cityWeather.getMonth().getCode();
        this.maxDegree = cityWeather.getMaxDegree();
        this.minDegree = cityWeather.getMinDegree();
        this.averageDegree = cityWeather.getAverageDegree();
    }
}
