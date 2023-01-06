package com.lam.liveamonthapp.domain.city.dto.response;


import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;
import com.lam.liveamonthapp.util.StringUtil;

@Getter
@Builder
public class SimpleCityInfoResponse {

    private Long id;
    private String cityName;
    private String cityInfoCat;
    private String content;

    public static SimpleCityInfoResponse of(CityIntro cityIntro) {
        return SimpleCityInfoResponse.builder()
                .id(cityIntro.getId())
                .cityName(cityIntro.getName().getCode())
                .cityInfoCat(cityIntro.getCityInfoCat().getCode())
                .content(StringUtil.previewContent(cityIntro.getContent()))
                .build();
    }
}
