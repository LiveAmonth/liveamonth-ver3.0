package teamproject.lam_server.domain.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.util.DateTimeUtil;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public abstract class BaseCommentResponse {
    protected Long commentId;
    protected String comment;
    protected CommentProfileResponse profile;
    protected String elapsedTime;
    protected Integer numberOfLikes;
    protected Integer numberOfDislikes;

    public BaseCommentResponse(Long commentId, String comment, CommentProfileResponse profile, LocalDateTime createdDate, Integer numberOfLikes, Integer numberOfDislikes) {
        this.commentId = commentId;
        this.comment = comment;
        this.profile = profile;
        this.elapsedTime = createdDate != null ? DateTimeUtil.calcTimeBefore(createdDate) : "";
        this.numberOfLikes = numberOfLikes;
        this.numberOfDislikes = numberOfDislikes;
    }
}
