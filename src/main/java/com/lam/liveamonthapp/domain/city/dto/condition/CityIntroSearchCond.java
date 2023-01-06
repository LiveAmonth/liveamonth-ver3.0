package com.lam.liveamonthapp.domain.city.dto.condition;

import lombok.*;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityIntroSearchCond {
    /**
     * 도시 이름, 카테고리, 이미지 확장자
     */
    private CityIntroCategory category;
    private CityName name;
}
