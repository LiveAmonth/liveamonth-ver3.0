package teamproject.lam_server.domain.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.TransportCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityTransport extends City {

    @Enumerated(EnumType.STRING)
    private TransportCategory cityTransportCat;

    private int stationCount;

    private int score;

    @Builder
    public CityTransport(CityName name, TransportCategory category, int stationCount,int score) {
        this.name = name;
        this.cityTransportCat = category;
        this.stationCount = stationCount;
        this.score = score;
    }

    public void updateStationCount(int stationCount) {
        this.stationCount = stationCount;
    }
}
