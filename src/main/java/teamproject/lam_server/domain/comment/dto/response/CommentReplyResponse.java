package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReplyResponse {

    private Long commentId;
    private Long parentId;
    private String content;
    private CommentProfileResponse profile;
    private String elapsedTime;

    public CommentReplyResponse(Long commentId, Long parentId, String content, CommentProfileResponse profile, String elapsedTime) {
        this.commentId = commentId;
        this.parentId = parentId;
        this.content = content;
        this.profile = profile;
        this.elapsedTime = elapsedTime;
    }
}
