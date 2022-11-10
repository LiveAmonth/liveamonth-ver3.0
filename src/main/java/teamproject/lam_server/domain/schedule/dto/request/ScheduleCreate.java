package teamproject.lam_server.domain.schedule.dto.request;

import lombok.*;
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
    private String city;

    @NotNull
    private PeriodRequest period;

    @NotNull
    private boolean publicFlag;

    @AssertTrue
    public boolean isValidPeriod() {
        return getPeriod().getStartDate().isBefore(getPeriod().getEndDate());
    }

    @Builder
    public ScheduleCreate(String title, String city, PeriodRequest period, boolean publicFlag) {
        this.title = title;
        this.city = city;
        this.period = period;
        this.publicFlag = publicFlag;
    }

    public Schedule toEntity(Member member) {
        return Schedule.builder()
                .title(this.title)
                .cityName(CityName.valueOf(this.city))
                .period(this.period.toEntity())
                .publicFlag(this.publicFlag)
                .member(member)
                .build();
    }
}
