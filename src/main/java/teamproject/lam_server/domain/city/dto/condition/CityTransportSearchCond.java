package teamproject.lam_server.domain.city.dto.condition;

import lombok.*;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.TransportCategory;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityTransportSearchCond{

    /**
     * 도시 이름, 카테고리, 개수(countGoe, countLoe)
     */
    private TransportCategory category;
    private CityName name;
}
