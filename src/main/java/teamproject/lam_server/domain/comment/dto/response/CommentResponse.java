package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import reactor.util.annotation.Nullable;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.util.DateTimeUtil;

import java.util.List;
import java.util.stream.Collectors;

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

    public static <T extends CommentEntity> CommentResponse.CommentResponseBuilder of(T comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                .likes(comment.getLikeCount())
                .dislikes(comment.getDislikeCount());
    }

    public static <T extends CommentEntity> CommentResponse ofSingleEntity(@Nullable T comment) {
        if(comment != null){
            return CommentResponse.builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .profile(CommentProfileResponse.of(comment.getMember()))
                    .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                    .likes(comment.getLikeCount())
                    .dislikes(comment.getDislikeCount())
                    .commentReplies(
                            comment.getChildren().stream()
                                    .map(CommentReplyResponse::of)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
        return null;
    }
}
