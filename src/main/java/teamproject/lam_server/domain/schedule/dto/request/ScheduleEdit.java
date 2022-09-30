package teamproject.lam_server.domain.schedule.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.entity.Period;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleEdit {

    @NotBlank
    private String title;

    @NotNull
    private CityName city;

    @NotNull
    private Period period;

    private boolean publicFlag;

    @AssertTrue
    public boolean isValidPeriod() {
        return getPeriod().getStartDate().isBefore(getPeriod().getEndDate());
    }
}
