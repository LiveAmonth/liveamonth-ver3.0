package teamproject.lam_server.app.city.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import teamproject.lam_server.app.city.converter.TransportCategoryConverter;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.TransportCategory;

import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "cityTransportCat","station_count"})
public class CityTransport extends City{

    @Convert(converter = TransportCategoryConverter.class)
    private TransportCategory cityTransportCat;

    private int station_count;

    private int score;

    public CityTransport(CityName name, TransportCategory cityTransportCat, int station_count) {
        this.name = name;
        this.cityTransportCat = cityTransportCat;
        this.station_count = station_count;
        this.score = this.cityTransportCat.getScore();
    }
}
