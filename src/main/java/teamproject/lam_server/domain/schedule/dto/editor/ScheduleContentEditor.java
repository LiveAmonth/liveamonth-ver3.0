package teamproject.lam_server.domain.schedule.dto.editor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.global.entity.TimePeriod;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleContentEditor {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private TimePeriod timePeriod;

    @Min(0)
    private int cost;

    @Builder
    public ScheduleContentEditor(String title, String content, TimePeriod timePeriod, int cost) {
        this.title = title;
        this.content = content;
        this.timePeriod = timePeriod;
        this.cost = cost;
    }
}
