package teamproject.lam_server.domain.schedule.entity;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.entity.Period;

@Getter
public class ScheduleEditor {

    private final String title;

    private final boolean publicFlag;

    private final CityName city;

    private final Period period;

    @Builder
    public ScheduleEditor(String title, boolean publicFlag, CityName city, Period period) {
        this.title = title;
        this.publicFlag = publicFlag;
        this.city = city;
        this.period = period;
    }
}
