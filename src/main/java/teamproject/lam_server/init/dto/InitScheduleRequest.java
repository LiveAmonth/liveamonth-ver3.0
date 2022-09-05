package teamproject.lam_server.init.dto;

import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.Period;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class InitScheduleRequest {

    @NotNull
    private Long memberId;

    @NotBlank
    private String title;

    @NotNull
    private boolean publicFlag;

    @NotNull
    private CityName city;

    @NotNull
    private Period period;

    public Schedule toEntity(Member member) {
        return Schedule.builder()
                .title(title)
                .member(member)
                .cityName(city)
                .period(period)
                .publicFlag(publicFlag)
                .build();
    }

}
