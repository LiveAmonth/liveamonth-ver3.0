package com.lam.liveamonthapp.domain.city.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.TransportCategory;
import com.lam.liveamonthapp.domain.city.entity.CityTransport;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityTransportCreate {

    @NotNull
    private CityName name;
    @NotNull
    private TransportCategory category;
    @PositiveOrZero
    private int stationCount;
    @Range(min = 0,max = 5)
    private int score;

    public CityTransport toEntity() {
        return CityTransport.builder()
                .name(name)
                .category(category)
                .stationCount(stationCount)
                .score(score)
                .build();
    }

}
