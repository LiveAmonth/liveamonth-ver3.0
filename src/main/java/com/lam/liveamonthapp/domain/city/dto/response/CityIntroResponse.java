package com.lam.liveamonthapp.domain.city.dto.response;


import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;

@Getter
@Builder
public class CityIntroResponse {

    private CityIntroCategory category;
    private String content;
    private String image;

    public static CityIntroResponse of(CityIntro cityIntro) {
        return CityIntroResponse.builder()
                .category(cityIntro.getCityInfoCat())
                .content(cityIntro.getContent())
                .image(cityIntro.getImage())
                .build();
    }
}
