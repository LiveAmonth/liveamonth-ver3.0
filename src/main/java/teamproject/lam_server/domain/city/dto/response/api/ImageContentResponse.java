package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityIntro;

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
