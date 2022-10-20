package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@Builder
public class EditableScheduleResponse {

    private Long id;
    private String title;
    private CityName city;
    private PeriodResponse period;
    private boolean publicFlag;

    public static EditableScheduleResponse of(Schedule schedule) {
        return EditableScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .city(schedule.getCityName())
                .period(PeriodResponse.of(schedule.getPeriod()))
                .publicFlag(schedule.getPublicFlag())
                .build();
    }
}
