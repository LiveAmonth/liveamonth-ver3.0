package teamproject.lam_server.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@AllArgsConstructor
public class ScheduleCardResponse {

    private Long id;
    private String title;
    private CityName city;
    private SimpleProfileResponse profile;
    private long cost;
    private long numberOfHits;
    private long numberOfLikes;
    private long numberOfComments;
    private PeriodResponse period;
    private boolean publicFlag;
}
