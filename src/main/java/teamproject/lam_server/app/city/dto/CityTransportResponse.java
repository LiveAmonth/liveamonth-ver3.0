package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityTransport;
import teamproject.lam_server.constants.CategoryConstants;

import static teamproject.lam_server.constants.CategoryConstants.*;

@Data
public class CityTransportResponse {
    private Long id;
    private String name;
    private String category;
    private int station_count;
    private int score;

    public CityTransportResponse(CityTransport cityTransport) {
        this.id = cityTransport.getId();
        this.name = cityTransport.getName().getCode();
        this.category = cityTransport.getCityTransportCat().getCode();
        this.station_count = cityTransport.getStationCount();
        this.score = cityTransport.getScore();
    }
}
