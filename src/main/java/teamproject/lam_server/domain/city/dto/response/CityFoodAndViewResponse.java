package teamproject.lam_server.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.entity.CityInfo;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder
public class CityFoodAndViewResponse {

    private List<CityInfoImageResponse> foodInfos;

    private List<CityInfoImageResponse> viewInfos;

    public static CityFoodAndViewResponse of(List<CityInfo> foodInfos, List<CityInfo> viewInfos) {
        return CityFoodAndViewResponse.builder()
                .foodInfos(foodInfos.stream().map(CityInfoImageResponse::of).collect(toList()))
                .viewInfos(viewInfos.stream().map(CityInfoImageResponse::of).collect(toList()))
                .build();
    }
}
