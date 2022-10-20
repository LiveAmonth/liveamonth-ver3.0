package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

import static teamproject.lam_server.util.DateTimeUtil.calcTimeBefore;

@Getter
@Builder
public class BestCommentResponse {

    private String comment;
    private CommentProfileResponse profile;
    private String elapsedTime;
    private long numberOfLikes;
    private long numberOfDislikes;

    public static BestCommentResponse of(@Nullable ScheduleComment comment) {
        if(comment == null) return null;
        return BestCommentResponse.builder()
                .comment(comment.getComment())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(calcTimeBefore(comment.getCreatedDate()))
                .numberOfLikes(comment.getNumberOfLikes())
                .numberOfDislikes(comment.getNumberOfDislikes())
                .build();
    }

}
