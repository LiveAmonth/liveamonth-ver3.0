package teamproject.lam_server.app.city.entity;

import lombok.*;
import teamproject.lam_server.app.city.converter.TransportCategoryConverter;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.TransportCategory;

import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "cityTransportCat", "station_count"})
public class CityTransport extends City {

    @Convert(converter = TransportCategoryConverter.class)
    private TransportCategory cityTransportCat;

    private int stationCount;

    private int score;

    @Builder
    public CityTransport(CityName name, TransportCategory cityTransportCat, int stationCount) {
        this.name = name;
        this.cityTransportCat = cityTransportCat;
        this.stationCount = stationCount;
        this.score = this.cityTransportCat.getScore();
    }

    public void updateStationCount(int stationCount) {
        this.stationCount = stationCount;
    }
}
