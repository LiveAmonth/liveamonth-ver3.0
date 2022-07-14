package teamproject.lam_server.domain.schedule.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateSchedule {

    @NotBlank
    private String title;

    @NotNull
    private boolean publicFlag;

    @NotBlank
    private String cityName;


}
