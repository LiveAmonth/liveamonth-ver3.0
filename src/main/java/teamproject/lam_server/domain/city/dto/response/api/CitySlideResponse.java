package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityIntro;

@Getter
@Builder
public class CitySlideResponse {

    private String name;
    private String image;

    public static CitySlideResponse of(CityIntro cityIntro) {
        return CitySlideResponse.builder()
                .name(cityIntro.getName().getCode())
                .image(cityIntro.getImage())
                .build();
    }
}
