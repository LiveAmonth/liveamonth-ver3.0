package teamproject.lam_server.domain.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class ScheduleContentCreate {

    @NotNull
    private Long scheduleId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    private int cost;

    public ScheduleContent toEntity(Schedule schedule){
        return ScheduleContent.builder()
                .title(title)
                .content(content)
                .date(date)
                .cost(cost)
                .schedule(schedule)
                .build();
    }
}
