package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityIntro;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
public class CityFoodAndViewResponse {

    private List<ImageContentResponse> foodInfos;

    private List<ImageContentResponse> viewInfos;

    public static CityFoodAndViewResponse of(List<CityIntro> foodInfos, List<CityIntro> viewInfos) {
        return CityFoodAndViewResponse.builder()
                .foodInfos(foodInfos.stream().map(ImageContentResponse::of).collect(toList()))
                .viewInfos(viewInfos.stream().map(ImageContentResponse::of).collect(toList()))
                .build();
    }
}
