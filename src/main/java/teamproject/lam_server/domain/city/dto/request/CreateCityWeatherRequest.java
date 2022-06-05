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
    private float avg;
    @NotNull
    private float min;
    @NotNull
    private float max;

    public CityWeather toEntity() {
        return CityWeather.builder()
                .name(name)
                .month(month)
                .averageDegree(avg)
                .maxDegree(max)
                .minDegree(min)
                .build();
    }
}
