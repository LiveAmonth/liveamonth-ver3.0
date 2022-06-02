package teamproject.lam_server.domain.city.dto.request;

import lombok.Data;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.entity.CityWeather;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateCityWeatherRequest {
    @NotEmpty
    private CityName name;

    @NotEmpty
    private MonthCategory month;

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
