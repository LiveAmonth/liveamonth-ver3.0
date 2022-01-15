package teamproject.lam_server.app.city.entity;

import lombok.Getter;
import teamproject.lam_server.constants.CategoryConstants.Month;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "city_weather")
@Getter
public class CityWeather extends City{

    @Enumerated(EnumType.STRING)
    private Month month;

    private float max_degree;
    private float min_degree;
    private float average_degree;
}
