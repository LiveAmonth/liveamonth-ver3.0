package teamproject.lam_server.domain.schedule.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.request.PeriodRequest;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCreate {

    @NotBlank
    private String title;

    @NotNull
    private CityName city;

    @NotNull
    private PeriodRequest period;

    private boolean publicFlag;

    @AssertTrue
    public boolean isValidPeriod() {
        return getPeriod().getStartDate().isBefore(getPeriod().getEndDate());
    }

    public Schedule toEntity(Member member) {
        return Schedule.builder()
                .title(this.title)
                .cityName(this.city)
                .period(this.period.toEntity())
                .publicFlag(this.publicFlag)
                .member(member)
                .build();
    }
}
