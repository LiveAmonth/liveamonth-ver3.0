package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@Builder
public class MyScheduleCardResponse {

    private Long id;
    private String title;
    private CityName city;
    private long cost;
    private long numberOfHits;
    private long numberOfLikes;
    private long numberOfComments;
    private PeriodResponse period;

    public static MyScheduleCardResponse of(Schedule schedule) {
        return MyScheduleCardResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .cost(schedule.getTotalCost())
                .city(schedule.getCityName())
                .numberOfHits(schedule.getNumberOfHits())
                .numberOfLikes(schedule.getNumberOfLikes())
                .numberOfComments(schedule.getNumberOfComments())
                .period(PeriodResponse.of(schedule.getPeriod()))
                .build();
    }

}
