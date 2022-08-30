package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.util.DateTimeUtil;

import java.util.List;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;
    private String content;
    private CommentProfileResponse profile;
    private List<CommentReplyResponse> commentReplies;
    private String elapsedTime;
    private int likes;
    private int dislikes;

    public static CommentResponse.CommentResponseBuilder of(ScheduleComment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .likes(comment.getLikeCount())
                .dislikes(comment.getDislikeCount());
    }
}
