package teamproject.lam_server.global.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import teamproject.lam_server.global.entity.Period;

import java.time.LocalDate;

@Getter
@ToString
@Builder
public class PeriodResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public static PeriodResponse of(Period period) {
        return PeriodResponse.builder()
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .build();
    }
}
