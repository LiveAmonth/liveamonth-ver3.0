package com.lam.liveamonthapp.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;

@Getter
@Builder
public class ImageContentResponse {

    private String content;
    private String image;

    public static ImageContentResponse of(CityIntro cityIntro) {
        return ImageContentResponse.builder()
                .content(cityIntro.getContent())
                .image(cityIntro.getImage())
                .build();
    }
}
