package teamproject.lam_server.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@AllArgsConstructor
public class EditableScheduleResponse {

    private Long id;
    private String title;
    private CityName city;
    private PeriodResponse period;
    private boolean publicFlag;
}
