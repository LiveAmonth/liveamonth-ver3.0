package teamproject.lam_simple.domain;

import lombok.Getter;
import lombok.Setter;
import teamproject.lam_simple.constants.CategoryConstants.Month;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "city_weather")
@Getter @Setter
public class CityWeather {

    @Id @GeneratedValue
    @Column(name = "city_weather_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private Month month;

    private float max_degree;
    private float min_degree;
    private float average_degree;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city_id")
    private City city;

}
