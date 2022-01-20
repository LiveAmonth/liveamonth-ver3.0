package teamproject.lam_server.app.city.dto.view;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

@Data
public class CitySlideResponse {

    private String name;
    private String image;

    public CitySlideResponse(CityInfo cityInfo) {
        this.name = cityInfo.getName().getValue();
        this.image = cityInfo.getImage();
    }
}
