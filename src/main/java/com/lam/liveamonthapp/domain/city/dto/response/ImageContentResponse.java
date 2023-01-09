package com.lam.liveamonthapp.domain.city.dto.response;

import com.lam.liveamonthapp.domain.city.dto.CityIntroDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageContentResponse {

    private String content;
    private String image;

    public static ImageContentResponse of(CityIntroDTO cityIntro) {
        return ImageContentResponse.builder()
                .content(cityIntro.getContent())
                .image(cityIntro.getImage())
                .build();
    }
}
