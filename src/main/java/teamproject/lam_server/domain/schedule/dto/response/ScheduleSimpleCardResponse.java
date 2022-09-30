package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@Builder
public class ScheduleSimpleCardResponse {

    private Long id;
    private Long memberId;
    private String title;
    private CityName city;
    private int cost;
    private int hits;
    private int likes;
    private PeriodResponse period;
    private boolean publicFlag;

    public static ScheduleSimpleCardResponse of(Schedule schedule) {
        return ScheduleSimpleCardResponse.builder()
                .id(schedule.getId())
                .memberId(schedule.getMember().getId())
                .title(schedule.getTitle())
                .cost(schedule.getTotalCost())
                .city(schedule.getCityName())
                .hits(schedule.getViewCount())
                .likes(schedule.getLikeCount())
                .period(PeriodResponse.of(schedule.getPeriod()))
                .publicFlag(schedule.getPublicFlag())
                .build();
    }
}
