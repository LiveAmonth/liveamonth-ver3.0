package teamproject.lam_server.domain.city.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityTransport;

@Getter
@Builder
public class CityTransportResponse {
    private Long id;
    private CityName name;
    private String category;
    private int station_count;
    private int score;

    public static CityTransportResponse of(CityTransport cityTransport) {
        return CityTransportResponse.builder()
                .id(cityTransport.getId())
                .name(cityTransport.getName())
                .category(cityTransport.getCityTransportCat().getCode())
                .station_count(cityTransport.getStationCount())
                .score(cityTransport.getScore())
                .build();
    }
}
