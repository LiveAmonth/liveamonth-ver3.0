package teamproject.lam_server.app.city.dto.view;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

@Data
public class CityFoodAndViewResponse {

    private List<CityInfoImageResponse> foodInfos = new ArrayList<>();

    private List<CityInfoImageResponse> viewInfos = new ArrayList<>();

    public CityFoodAndViewResponse(List<CityInfo> foodInfos,List<CityInfo> viewInfos) {
        this.foodInfos = foodInfos.stream().map(CityInfoImageResponse::new).collect(toList());

        this.viewInfos = viewInfos.stream().map(CityInfoImageResponse::new).collect(toList());
    }
}
