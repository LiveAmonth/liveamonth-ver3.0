package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityIntro;

@Getter
@Builder
public class CityIntroImageResponse {

    private String content;
    private String image;

    public static CityIntroImageResponse of(CityIntro cityIntro) {
        return CityIntroImageResponse.builder()
                .content(cityIntro.getContent())
                .image(cityIntro.getImage())
                .build();
    }
}
