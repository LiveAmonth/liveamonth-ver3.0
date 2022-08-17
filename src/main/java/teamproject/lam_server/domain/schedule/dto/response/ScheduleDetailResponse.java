package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.schedule.entity.Schedule;

import java.util.List;
import java.util.stream.Collectors;

/**
 * for other, my schedule view
 */
@Getter
@Builder
public class ScheduleDetailResponse {

    private ScheduleCardResponse card;
    private List<ScheduleContentResponse> contents;

    public static ScheduleDetailResponse of(Schedule schedule){
        return ScheduleDetailResponse.builder()
                .card(
                        ScheduleCardResponse.of(schedule)
                )
                .contents(
                        schedule.getScheduleContents().stream()
                        .map(ScheduleContentResponse::of)
                        .collect(Collectors.toList())
                )
                .build();
    }

}
