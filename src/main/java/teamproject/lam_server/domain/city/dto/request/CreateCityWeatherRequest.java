package teamproject.lam_server.domain.city.dto.request;

import lombok.Data;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.entity.CityWeather;

import javax.validation.constraints.NotNull;

@Data
public class CreateCityWeatherRequest {

    @NotNull
    private CityName name;
    @NotNull
    private MonthCategory month;
    @NotNull
    private float minDegree;
    @NotNull
    private float maxDegree;

    public CityWeather toEntity() {
        return CityWeather.builder()
                .name(name)
                .month(month)
                .minDegree(minDegree)
                .maxDegree(maxDegree)
                .build();
    }
}
