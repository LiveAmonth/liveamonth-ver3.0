package teamproject.lam_server.domain.schedule.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import teamproject.lam_server.domain.city.constants.CityName;

import java.time.LocalDate;

@Getter
@ToString
@Setter
public class ScheduleSearchCond{

    private String memberNickname;

    private CityName cityName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
}
