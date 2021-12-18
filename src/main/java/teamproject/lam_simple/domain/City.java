package teamproject.lam_simple.domain;

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

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "city-with-cityInfos",
                attributeNodes = {
                        @NamedAttributeNode("cityInfos"),
                }),
        @NamedEntityGraph(
                name = "city-with-cityWeathers",
                attributeNodes = {
                        @NamedAttributeNode("cityWeathers")
                }),
        @NamedEntityGraph(
                name = "city-with-cityTransports",
                attributeNodes = {
                        @NamedAttributeNode("cityTransports")
                })
})
@Entity
@Table(name = "cities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City {

    @Id @GeneratedValue
    @Column(name = "city_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private CityNames name;

    @OneToMany(mappedBy = "city")
    private List<CityInfo> cityInfos = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    private List<CityWeather> cityWeathers = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    private List<CityTransport> cityTransports = new ArrayList<>();


    @Builder
    public City(CityNames name) {
        Assert.notNull(name, "name must not be null");
        this.name = name;
    }

    //==비즈니스 로직==//
    public CityTransportGrade getTransportScore(){
        int score = 0;
        for (CityTransport cityTransport : cityTransports) {
            int count = cityTransport.getStation_count();
            score += count * cityTransport.getTransport_category().getScore();
        }
        if(score > 50) return T_GOOD;
        else if(score>21 && score<=50) return T_FAIR;
        else return T_POOR;
    }

    public float getAVGTemp(CategoryConstants.Month month){
        return cityWeathers.get(month.ordinal()).getAverage_degree();
    }
}
