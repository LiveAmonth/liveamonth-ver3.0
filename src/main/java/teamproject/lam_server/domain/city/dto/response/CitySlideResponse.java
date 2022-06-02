package teamproject.lam_server.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityInfo;

@Getter
@Builder
public class CitySlideResponse {

    private String name;
    private String image;

    public static CitySlideResponse of(CityInfo cityInfo) {
        return CitySlideResponse.builder()
                .name(cityInfo.getName().getCode())
                .image(cityInfo.getImage())
                .build();
    }
}
