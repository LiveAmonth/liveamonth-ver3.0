package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityWeather;

@Data
public class CityWeatherResponse {

    private String cityName;

    private String month;

    private float maxDegree;
    private float minDegree;
    private float averageDegree;

    public CityWeatherResponse(CityWeather cityWeather) {
        this.cityName = cityWeather.getName().getValue();
        this.month = cityWeather.getMonth().getValue();
        this.maxDegree = cityWeather.getMaxDegree();
        this.minDegree = cityWeather.getMinDegree();
        this.averageDegree = cityWeather.getAverageDegree();
    }
}
