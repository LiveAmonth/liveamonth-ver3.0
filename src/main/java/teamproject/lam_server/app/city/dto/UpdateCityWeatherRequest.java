package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.entity.CityTransport;
import teamproject.lam_server.app.city.entity.CityWeather;

import javax.validation.constraints.NotEmpty;

import static teamproject.lam_server.constants.CategoryConstants.CityName;
import static teamproject.lam_server.constants.CategoryConstants.Month;

@Data
public class UpdateCityWeatherRequest {
    @NotEmpty
    private CityName name;

    @NotEmpty
    private Month month;

    private float maxDegree;
    private float minDegree;

    public CityWeather toEntity() {
        return CityWeather.builder()
                .name(this.name)
                .month(this.month)
                .maxDegree(this.maxDegree)
                .minDegree(this.minDegree)
                .build();
    }
}
