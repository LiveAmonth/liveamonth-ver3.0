package teamproject.lam_server.domain.city.dto.request;

import lombok.Data;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.TransportCategory;
import teamproject.lam_server.domain.city.entity.CityTransport;

import javax.validation.constraints.NotNull;

@Data
public class CreateCityTransportRequest {
    @NotNull
    private CityName name;
    @NotNull
    private TransportCategory category;

    public CityTransport toEntity() {
        return CityTransport.builder()
                .name(name)
                .category(category)
                .build();
    }
}
