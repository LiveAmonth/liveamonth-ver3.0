package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;

@Getter
@Builder
public class ScheduleCardResponse {

    private Long id;
    private String title;
    private CityName city;
    private SimpleProfileResponse profile;
    private int cost;
    private int hits;
    private int likes;
    private int comments;
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
                .hits(schedule.getViewCount())
                .likes(schedule.getLikeCount())
                .comments(schedule.getCommentCount())
                .period(PeriodResponse.of(schedule.getPeriod()))
                .publicFlag(schedule.getPublicFlag())
                .comment(comment)
                .build();
    }
}
