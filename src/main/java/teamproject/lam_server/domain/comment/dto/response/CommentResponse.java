package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.util.DateTimeUtil;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;
    private Long parentId;
    private String comment;
    private CommentProfileResponse profile;
    private List<CommentResponse> commentReplies;
    private String elapsedTime;
    private long numberOfLikes;
    private long numberOfDislikes;

    public static <T extends CommentEntity> CommentResponse.CommentResponseBuilder of(T comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .numberOfLikes(comment.getNumberOfLikes())
                .numberOfDislikes(comment.getNumberOfDislikes());
    }

    public static <T extends CommentEntity> CommentResponse ofReply(Long parentId, T comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .parentId(parentId)
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .numberOfLikes(comment.getNumberOfLikes())
                .numberOfDislikes(comment.getNumberOfDislikes())
                .commentReplies(Collections.emptyList())
                .build();
    }
}
