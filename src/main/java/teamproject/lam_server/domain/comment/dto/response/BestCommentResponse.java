package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.entity.CommentEntity;

import static teamproject.lam_server.util.DateTimeUtil.calcTimeBefore;

@Getter
@Builder
public class BestCommentResponse {

    private Long commentId;
    private String comment;
    private CommentProfileResponse profile;
    private String elapsedTime;
    private long numberOfLikes;
    private long numberOfDislikes;

    public static <T extends CommentEntity> BestCommentResponse of(T t) {
        return BestCommentResponse.builder()
                .commentId(t.getId())
                .comment(t.getComment())
                .profile(CommentProfileResponse.of(t.getMember()))
                .elapsedTime(calcTimeBefore(t.getCreatedDate()))
                .numberOfLikes(t.getNumberOfLikes())
                .numberOfDislikes(t.getNumberOfDislikes())
                .build();
    }

}
