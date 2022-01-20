package teamproject.lam_server.app.city.dto;


import lombok.Data;
import teamproject.lam_server.app.city.entity.CityInfo;

@Data
public class CityInfoResponse {

    private Long id;
    private String cityName;
    private String cityInfoCat;
    private String content;

    public CityInfoResponse(CityInfo cityInfo) {
        this.id = cityInfo.getId();
        this.cityName = cityInfo.getName().name();
        this.cityInfoCat = cityInfo.getCityInfoCat().name();
        this.content = cityInfo.getContent();
    }
}
