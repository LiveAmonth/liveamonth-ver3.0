package teamproject.lam_server.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.dto.PeriodCond;

import java.time.LocalDate;

@Getter
public class ScheduleSearchCond extends PeriodCond {

    private String memberNickname;

    private CityName cityName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
}
