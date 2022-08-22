package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.util.DateTimeUtil;

@Getter
@Builder
public class CommentReplyResponse {

    private final Long commentId;
    private final Long parentId;
    private final String content;
    private final CommentProfileResponse profile;
    private final String elapsedTime;

    public static CommentReplyResponse of(ScheduleComment comment) {
        return CommentReplyResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent().getId())
                .content(comment.getContent())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .build();
    }
}
