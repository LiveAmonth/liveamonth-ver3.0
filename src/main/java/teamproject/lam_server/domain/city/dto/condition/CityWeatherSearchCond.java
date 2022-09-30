package teamproject.lam_server.domain.city.dto.condition;

import lombok.*;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityWeatherSearchCond{

    /**
     * 도시 이름, 월
     */
    private MonthCategory month;
    private CityName name;

}
