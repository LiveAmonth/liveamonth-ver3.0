package teamproject.lam_server.domain.schedule.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import teamproject.lam_server.domain.city.constants.CityName;

import java.time.LocalDate;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class ScheduleSearchCond{

    private String memberNickname;

    private String title;

    private CityName cityName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
}
