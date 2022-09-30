package teamproject.lam_server.global.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import teamproject.lam_server.global.entity.TimePeriod;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class TimePeriodResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDateTime;

    public static TimePeriodResponse of(TimePeriod timePeriod) {
        return TimePeriodResponse.builder()
                .startDateTime(timePeriod.getStartDateTime())
                .endDateTime(timePeriod.getEndDateTime())
                .build();
    }
}
