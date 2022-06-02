package teamproject.lam_server.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityInfo;

@Getter
@Builder
public class CityInfoImageResponse {

    private String content;
    private String image;

    public static CityInfoImageResponse of(CityInfo cityInfo) {
        return CityInfoImageResponse.builder()
                .content(cityInfo.getContent())
                .image(cityInfo.getImage())
                .build();
    }
}
