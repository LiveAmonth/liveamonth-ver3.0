package teamproject.lam_server.domain.city.dto.condition;

import lombok.Data;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;

@Data
public class CityWeatherSearchCond{

    /**
     * 도시 이름, 월
     */
    private MonthCategory month;
    private CityName name;

}
