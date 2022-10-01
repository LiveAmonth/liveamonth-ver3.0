package teamproject.lam_server.domain.city.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityTransportEdit {

    private int stationCount;

}
