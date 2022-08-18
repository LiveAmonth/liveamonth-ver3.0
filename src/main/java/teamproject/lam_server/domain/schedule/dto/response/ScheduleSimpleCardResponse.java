package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.Period;

@Getter
@Builder
public class ScheduleSimpleCardResponse {

    private Long id;
    private String title;
    private int cost;
    private CityName city;
    private int hits;
    private int likes;
    private Period period;

    public static ScheduleSimpleCardResponse of(Schedule schedule) {
        return ScheduleSimpleCardResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .cost(schedule.getTotalCost())
                .city(schedule.getCityName())
                .hits(schedule.getViewCount())
                .likes(schedule.getLikeCount())
                .period(schedule.getPeriod())
                .build();
    }
}
