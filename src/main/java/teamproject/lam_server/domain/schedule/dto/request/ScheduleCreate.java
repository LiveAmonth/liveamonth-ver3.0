package teamproject.lam_server.domain.schedule.dto.request;

import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.Period;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ScheduleCreate {

    @NotNull
    private Long memberId;

    @NotBlank
    private String title;

    @NotNull
    private boolean publicFlag;

    @NotBlank
    private CityName cityName;

    private Period period;

    public Schedule toEntity(Member member) {
        return Schedule.builder()
                .title(title)
                .cityName(cityName)
                .publicFlag(publicFlag)
                .period(period)
                .member(member)
                .build();
    }

}
