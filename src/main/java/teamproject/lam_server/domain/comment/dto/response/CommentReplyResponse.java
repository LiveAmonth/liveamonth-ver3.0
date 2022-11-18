package teamproject.lam_server.domain.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentReplyResponse extends BaseCommentResponse {

    private Long parentId;

    public CommentReplyResponse(
            Long commentId,
            String comment,
            CommentProfileResponse profile,
            LocalDateTime createdDate,
            Integer numberOfLikes,
            Integer numberOfDislikes,
            Long parentId) {
        super(commentId, comment, profile, createdDate, numberOfLikes, numberOfDislikes);
        this.parentId = parentId;
    }
}
