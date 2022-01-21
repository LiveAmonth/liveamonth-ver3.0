package teamproject.lam_server.app.city.dto.condition;

import lombok.Data;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.TransportCategory;

@Data
public class CityTransportSearchCond {

    /**
     * 도시 이름, 카테고리, 개수(countGoe, countLoe)
     */
    private CityName name;
    private TransportCategory category;
    private Integer countGoe;
    private Integer countLoe;
}
