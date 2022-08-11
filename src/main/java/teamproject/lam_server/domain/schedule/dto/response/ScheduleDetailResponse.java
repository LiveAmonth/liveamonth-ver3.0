package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

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

    public static ScheduleDetailResponse of(Schedule schedule, List<ScheduleContent> contents){
        return ScheduleDetailResponse.builder()
                .card(
                        ScheduleCardResponse.of(schedule)
                )
                .contents(
                        contents.stream()
                        .map(ScheduleContentResponse::of)
                        .collect(Collectors.toList())
                )
                .build();
    }

}
