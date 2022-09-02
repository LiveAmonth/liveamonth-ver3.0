package teamproject.lam_server.domain.schedule.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.Period;

/**
 * for schedule list view
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleCardResponse {

    private Long id;
    private String title;
    private SimpleProfileResponse profile;
    private int cost;
    private CityName city;
    private int hits;
    private int likes;
    private int comments;
    private Period period;
    private CommentResponse comment;

    public static ScheduleCardResponse of(Schedule schedule, @Nullable CommentResponse comment){
        return ScheduleCardResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .profile(SimpleProfileResponse.of(schedule.getMember()))
                .cost(schedule.getTotalCost())
                .city(schedule.getCityName())
                .hits(schedule.getViewCount())
                .likes(schedule.getLikeCount())
                .comments(schedule.getCommentCount())
                .period(schedule.getPeriod())
                .comment(comment)
                .build();
    }

}
