package teamproject.lam_server.app.city.dto.view;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityName;

@Data
public class CityGridDataResponse {
    private String name;
    private String image;
    private float averageDegree;
    private int transportScore;

    @QueryProjection
    public CityGridDataResponse(CityName name, String image, float averageDegree, int transportScore) {
        this.name = name.getCode();
        this.image = image;
        this.averageDegree = averageDegree;
        this.transportScore = transportScore;
    }
}
