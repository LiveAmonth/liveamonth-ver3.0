package teamproject.lam_server.app.city.entity;

import lombok.*;
import teamproject.lam_server.app.city.converter.MonthConverter;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.Month;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityWeather extends City {

    @Convert(converter = MonthConverter.class)
    private Month month;

    private float maxDegree;
    private float minDegree;
    private float averageDegree;

    @Builder
    public CityWeather(CityName name, Month month, float maxDegree, float minDegree) {
        this.name = name;
        this.month = month;
        this.maxDegree = maxDegree;
        this.minDegree = minDegree;
        this.averageDegree = (maxDegree + minDegree) / 2;
    }


}
