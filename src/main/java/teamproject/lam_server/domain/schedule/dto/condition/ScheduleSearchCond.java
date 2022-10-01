package teamproject.lam_server.domain.schedule.dto.condition;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
}
