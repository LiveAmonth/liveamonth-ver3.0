package teamproject.lam_server.app.city.dto.condition;

import lombok.Data;

import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import static teamproject.lam_server.constants.CategoryConstants.CityName;

@Data
public class CityInfoSearchCond {
    /**
     * 도시 이름, 카테고리, 이미지 확장자
     */
    private CityName name;
    private CityInfoCategory category;
    private String imageExtension;

}
