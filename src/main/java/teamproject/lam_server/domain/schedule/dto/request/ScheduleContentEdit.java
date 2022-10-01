package teamproject.lam_server.domain.schedule.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import teamproject.lam_server.global.dto.request.TimePeriodRequest;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleContentEdit {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private TimePeriodRequest timePeriod;

    @Min(0)
    private int cost;

    @AssertTrue
    public boolean isValidTimePeriod() {
        return getTimePeriod().getStartDateTime().isBefore(getTimePeriod().getEndDateTime());
    }
}
