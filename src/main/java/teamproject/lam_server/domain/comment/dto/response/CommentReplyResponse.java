package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.util.DateTimeUtil;

@Getter
@Builder
public class CommentReplyResponse {

    private Long commentId;
    private Long parentId;
    private String content;
    private CommentProfileResponse profile;
    private String elapsedTime;
    private int likes;
    private int dislikes;

    public static CommentReplyResponse of(ScheduleComment comment) {
        return CommentReplyResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent().getId())
                .content(comment.getContent())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .likes(comment.getLikeCount())
                .dislikes(comment.getDislikeCount())
                .build();
    }
}
