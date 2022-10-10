package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.util.DateTimeUtil;

@Getter
@Builder
public class CommentReplyResponse {

    private Long commentId;
    private Long parentId;
    private String comment;
    private CommentProfileResponse profile;
    private String elapsedTime;
    private long numberOfLikes;
    private long numberOfDislikes;

    public static <T extends CommentEntity> CommentReplyResponse of(T comment) {
        return CommentReplyResponse.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent().getId())
                .comment(comment.getComment())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .numberOfLikes(comment.getNumberOfLikes())
                .numberOfDislikes(comment.getNumberOfDislikes())
                .build();
    }
}
