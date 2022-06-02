package teamproject.lam_server.domain.city.dto.response;


import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityInfo;

@Getter
@Builder
public class CityInfoResponse {

    private Long id;
    private String cityName;
    private String cityInfoCat;
    private String content;
    private String image;

    public static CityInfoResponse of(CityInfo cityInfo) {
        return CityInfoResponse.builder()
                .id(cityInfo.getId())
                .cityName(cityInfo.getName().getCode())
                .cityInfoCat(cityInfo.getCityInfoCat().getCode())
                .content(cityInfo.getContent())
                .image(cityInfo.getImage())
                .build();
    }
}
