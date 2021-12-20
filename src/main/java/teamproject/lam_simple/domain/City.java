package teamproject.lam_simple.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.util.Assert;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;
import teamproject.lam_simple.constants.CategoryConstants.CityTransportGrade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static teamproject.lam_simple.constants.CategoryConstants.CityTransportGrade.*;

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
