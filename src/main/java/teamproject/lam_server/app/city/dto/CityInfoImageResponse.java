package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

@Data
public class CityInfoImageResponse {

    private String content;
    private String image;

    public CityInfoImageResponse(CityInfo cityInfo) {
        this.content = cityInfo.getContent();
        this.image = cityInfo.getImage();
    }
}
