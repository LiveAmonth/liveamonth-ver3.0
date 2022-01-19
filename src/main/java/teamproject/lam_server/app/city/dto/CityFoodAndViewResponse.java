package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Data
public class CityFoodAndViewResponse {

    private List<CityInfoResponse> foodInfos = new ArrayList<>();

    private List<CityInfoResponse> viewInfos = new ArrayList<>();

    public CityFoodAndViewResponse(List<CityInfo> foodInfos,List<CityInfo> viewInfos) {
        this.foodInfos = foodInfos.stream().map(CityInfoResponse::new).collect(toList());

        this.viewInfos = viewInfos.stream().map(CityInfoResponse::new).collect(toList());
    }
}
