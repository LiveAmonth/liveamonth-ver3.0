package teamproject.lam_server.domain.city.dto.response;


import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.entity.CityIntro;

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
