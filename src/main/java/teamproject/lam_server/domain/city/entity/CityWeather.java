package teamproject.lam_server.domain.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityWeather extends City {

    @Enumerated(EnumType.STRING)
    private MonthCategory month;

    private float maxDegree;
    private float minDegree;
    private float averageDegree;

    @Builder
    public CityWeather(CityName name, MonthCategory month, float averageDegree, float maxDegree, float minDegree) {
        this.name = name;
        this.month = month;
        this.averageDegree = averageDegree;
        this.maxDegree = maxDegree;
        this.minDegree = minDegree;
    }
}
