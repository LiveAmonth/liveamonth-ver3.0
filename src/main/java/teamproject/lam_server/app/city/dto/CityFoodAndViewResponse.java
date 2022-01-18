package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

@Data
public class CityFoodAndViewResponse {

    private String name;
    private String content;
    private String image;

    public CityFoodAndViewResponse(CityInfo cityInfo) {
        this.name = cityInfo.getName().getValue();
        this.content = cityInfo.getContent();
        this.image = cityInfo.getImage();
    }
}
