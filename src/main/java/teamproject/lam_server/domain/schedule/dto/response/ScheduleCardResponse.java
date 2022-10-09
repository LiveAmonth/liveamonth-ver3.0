package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;

import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class ScheduleCardResponse {

    private Long id;
    private String title;
    private CityName city;
    private SimpleProfileResponse profile;
    private long cost;
    private String numberOfHits;
    private String numberOfLikes;
    private String numberOfComments;
    private PeriodResponse period;
    private boolean publicFlag;
    private CommentResponse comment;

    public static ScheduleCardResponse of(Schedule schedule, @Nullable CommentResponse comment) {
        return ScheduleCardResponse.builder()
                .id(schedule.getId())
                .profile(SimpleProfileResponse.of(schedule.getMember()))
                .title(schedule.getTitle())
                .cost(schedule.getTotalCost())
                .city(schedule.getCityName())
                .numberOfHits(countFormat(schedule.getNumberOfHits()))
                .numberOfLikes(countFormat(schedule.getNumberOfLikes()))
                .numberOfComments(countFormat(schedule.getNumberOfComments()))
                .period(PeriodResponse.of(schedule.getPeriod()))
                .publicFlag(schedule.getPublicFlag())
                .comment(comment)
                .build();
    }
}
