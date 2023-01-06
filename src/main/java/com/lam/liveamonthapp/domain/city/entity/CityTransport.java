package com.lam.liveamonthapp.domain.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.TransportCategory;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "city_transport_id"))
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
