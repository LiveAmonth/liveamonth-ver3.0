package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

@Data
public class CityInfoResponse {

    private String content;
    private String image;

    public CityInfoResponse(CityInfo cityInfo) {
        this.content = cityInfo.getContent();
        this.image = cityInfo.getImage();
    }
}
