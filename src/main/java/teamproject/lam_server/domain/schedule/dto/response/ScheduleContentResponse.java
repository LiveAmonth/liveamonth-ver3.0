package teamproject.lam_server.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

import java.time.LocalDate;

@Getter
@Builder
public class ScheduleContentResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDate date;
    private String title;

    public static ScheduleContentResponse of(ScheduleContent schedulecontent) {
        return ScheduleContentResponse.builder()
                .date(schedulecontent.getDate())
                .title(schedulecontent.getTitle())
                .build();
    }

}
