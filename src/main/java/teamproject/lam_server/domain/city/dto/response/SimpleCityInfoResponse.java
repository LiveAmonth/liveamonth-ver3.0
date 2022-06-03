package teamproject.lam_server.domain.city.dto.response;


import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityIntro;
import teamproject.lam_server.util.SimpleResponseUtil;

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
                .content(SimpleResponseUtil.previewContent(cityIntro.getContent()))
                .build();
    }
}
