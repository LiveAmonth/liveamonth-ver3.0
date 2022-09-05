package teamproject.lam_server.domain.schedule.dto.editor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.entity.Period;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleEditor {

    @NotBlank
    private String title;

    @NotNull
    private boolean publicFlag;

    @NotNull
    private CityName city;

    @NotNull
    private Period period;

    @Builder
    public ScheduleEditor(String title, boolean publicFlag, CityName city, Period period) {
        this.title = title;
        this.publicFlag = publicFlag;
        this.city = city;
        this.period = period;
    }
}
