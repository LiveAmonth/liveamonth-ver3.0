package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import reactor.util.annotation.Nullable;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.util.DateTimeUtil;

import java.util.Collections;
import java.util.List;

import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;
    private Long parentId;
    private String comment;
    private CommentProfileResponse profile;
    private List<CommentResponse> commentReplies;
    private String elapsedTime;
    private String numberOfLikes;
    private String numberOfDislikes;

    public static <T extends CommentEntity> CommentResponse.CommentResponseBuilder of(T comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .numberOfLikes(countFormat(comment.getNumberOfLikes()))
                .numberOfDislikes(countFormat(comment.getNumberOfDislikes()));
    }

    public static <T extends CommentEntity> CommentResponse ofReply(Long parentId, T comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .parentId(parentId)
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .numberOfLikes(countFormat(comment.getNumberOfLikes()))
                .numberOfDislikes(countFormat(comment.getNumberOfDislikes()))
                .commentReplies(Collections.emptyList())
                .build();
    }

    public static <T extends CommentEntity> CommentResponse ofBest(@Nullable T comment) {
        if (comment != null) {
            return CommentResponse.builder()
                    .commentId(comment.getId())
                    .comment(comment.getComment())
                    .profile(CommentProfileResponse.of(comment.getMember()))
                    .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                    .numberOfLikes(countFormat(comment.getNumberOfLikes()))
                    .numberOfDislikes(countFormat(comment.getNumberOfDislikes()))
                    .commentReplies(Collections.emptyList())
                    .build();
        }
        return null;
    }
}
