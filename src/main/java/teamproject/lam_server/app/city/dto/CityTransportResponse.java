package teamproject.lam_server.app.city.dto;

import lombok.Data;
import teamproject.lam_server.app.city.entity.CityTransport;

@Data
public class CityTransportResponse {

    private String category;

    private int station_count;

    private int score;

    public CityTransportResponse(CityTransport cityTransport) {
        this.category = cityTransport.getCityTransportCat().getValue();
        this.station_count = cityTransport.getStation_count();
        this.score = cityTransport.getScore();
    }
}
