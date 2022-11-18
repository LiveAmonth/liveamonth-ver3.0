package teamproject.lam_server.domain.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BestCommentResponse extends BaseCommentResponse {

    public BestCommentResponse(Long commentId, String comment, CommentProfileResponse profile, LocalDateTime createdDate, Integer numberOfLikes, Integer numberOfDislikes) {
        super(commentId, comment, profile, createdDate, numberOfLikes, numberOfDislikes);
    }
}
