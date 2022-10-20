package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@Builder
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

    public static MyScheduleResponse of(Schedule schedule) {
        return MyScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .city(schedule.getCityName())
                .cost(schedule.getTotalCost())
                .period(PeriodResponse.of(schedule.getPeriod()))
                .publicFlag(schedule.getPublicFlag())
                .numberOfHits(schedule.getNumberOfHits())
                .numberOfLikes(schedule.getNumberOfLikes())
                .numberOfComments(schedule.getNumberOfComments())
                .build();
    }
}
