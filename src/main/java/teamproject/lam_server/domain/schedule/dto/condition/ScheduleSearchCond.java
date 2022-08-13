package teamproject.lam_server.domain.schedule.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.dto.PeriodCond;

import java.time.LocalDate;

@Getter
@ToString
public class ScheduleSearchCond extends PeriodCond {

    private String memberNickname;

    private CityName cityName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
}
