package teamproject.lam_server.domain.schedule.dto.response;


import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.global.dto.DateRange;

import java.util.List;

@Getter
@Builder
public class ScheduleCardResponse {

    private SimpleProfileResponse profile;
    private int price;
    private CityName city;
    private int hits;
    private int likes;
    private DateRange range;
    private List<ScheduleContentResponse> contents;

}
