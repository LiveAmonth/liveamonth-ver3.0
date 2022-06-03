package teamproject.lam_server.domain.city.dto.condition;

import lombok.Data;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;

@Data
public class CityIntroSearchCond {
    /**
     * 도시 이름, 카테고리, 이미지 확장자
     */
    private CityIntroCategory category;
    private CityName name;
}
