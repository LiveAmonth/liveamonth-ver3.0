package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;
    private Long parentId;
    private String content;
    private CommentProfileResponse profile;
    private List<CommentReplyResponse> commentReply;
    private String elapsedTime;

    @Builder
    public CommentResponse(Long commentId, Long parentId, String content, CommentProfileResponse profile, List<CommentReplyResponse> commentReply, String elapsedTime) {
        this.commentId = commentId;
        this.parentId = parentId;
        this.content = content;
        this.profile = profile;
        this.commentReply = commentReply;
        this.elapsedTime = elapsedTime;
    }


}
