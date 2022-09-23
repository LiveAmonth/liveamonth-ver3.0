package teamproject.lam_server.domain.schedule.dto.editor;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.entity.Period;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ScheduleEditor {

    @NotBlank
    private final String title;

    @NotNull
    private final boolean publicFlag;

    @NotNull
    private final CityName city;

    @NotNull
    private final Period period;

    @Builder
    public ScheduleEditor(String title, boolean publicFlag, CityName city, Period period) {
        this.title = title;
        this.publicFlag = publicFlag;
        this.city = city;
        this.period = period;
    }
}
