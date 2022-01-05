package teamproject.lam_server.app.city.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import teamproject.lam_server.constants.CategoryConstants.CityNames;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
@Getter
@NoArgsConstructor
public class City {

    @Id @GeneratedValue
    @Column(name = "city_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private CityNames name;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<CityInfo> cityInfos = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<CityWeather> cityWeathers = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<CityTransport> cityTransports = new ArrayList<>();

}
