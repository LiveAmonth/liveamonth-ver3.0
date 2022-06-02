package teamproject.lam_server.domain.city.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import teamproject.lam_server.domain.city.constants.CityName;

@Data
public class CityGridDataResponse {
    private CityName name;
    private String image;
    private float averageDegree;
    private int transportScore;

    @QueryProjection
    public CityGridDataResponse(CityName name, String image, float averageDegree, int transportScore) {
        this.name = name;
        this.image = image;
        this.averageDegree = averageDegree;
        this.transportScore = transportScore;
    }
}
