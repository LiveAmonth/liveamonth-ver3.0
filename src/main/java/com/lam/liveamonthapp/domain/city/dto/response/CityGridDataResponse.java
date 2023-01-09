package com.lam.liveamonthapp.domain.city.dto.response;

import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;

import static com.lam.liveamonthapp.constants.AttrConstants.IMAGEBB_URL;

@Getter
public class CityGridDataResponse {
    private final CityName name;
    private final String image;
    private final float averageDegree;
    private final int transportScore;

    public CityGridDataResponse(CityName name, String image, float averageDegree, int transportScore) {
        this.name = name;
        this.image = IMAGEBB_URL + image;
        this.averageDegree = averageDegree;
        this.transportScore = transportScore;
    }
}
