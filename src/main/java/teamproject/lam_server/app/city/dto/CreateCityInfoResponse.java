package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.constants.CategoryConstants;

@Data
public class CreateCityInfoResponse {
    private Long id;
    private CategoryConstants.CityInfoCategory category;
    private String Content;
    private String image;

}
