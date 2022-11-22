package teamproject.lam_server.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@AllArgsConstructor
public class MyScheduleResponse {

    private Long id;
    private String title;
    private CityName city;
    private long cost;
    private PeriodResponse period;
    private boolean publicFlag;
    private long numberOfHits;
    private long numberOfLikes;
    private long numberOfComments;
}
