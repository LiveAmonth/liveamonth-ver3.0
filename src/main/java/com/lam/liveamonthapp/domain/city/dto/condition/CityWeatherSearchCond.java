package com.lam.liveamonthapp.domain.city.dto.condition;

import lombok.*;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityWeatherSearchCond{

    /**
     * 도시 이름, 월
     */
    private MonthCategory month;
    private CityName name;

}
