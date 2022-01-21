package teamproject.lam_server.app.city.dto.condition;

import lombok.Data;
import teamproject.lam_server.constants.CategoryConstants;

@Data
public class CityWeatherSearchCond {

    /**
     * 도시 이름, 월, 온도(degreeGoe, degreeLoe)
     */
    private CategoryConstants.CityName name;
    private CategoryConstants.Month month;
    private Integer degreeGoe;
    private Integer degreeLoe;
}
