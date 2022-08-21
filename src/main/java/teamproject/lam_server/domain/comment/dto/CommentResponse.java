package teamproject.lam_server.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.dto.response.CommentProfileResponse;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

import java.util.List;
import java.util.stream.Collectors;

import static teamproject.lam_server.util.DateTimeUtil.calcTimeBefore;

@Getter
@Builder
public class CommentResponse<T extends CommentEntity> {

    private String content;
    private CommentProfileResponse profile;
    private List<CommentResponse<T>> commentReply;
    private String writeTimeBefore;

    public static <T extends CommentEntity> CommentResponse<T> of(T t) {
        return CommentResponse.builder()
                .content(t.getContent())
                .profile(CommentProfileResponse.of(t.getMember()))
                .writeTimeBefore(t.getChildren().stream().map().collect(Collectors.toList()))
                .commentReply(

                )
                .build();
    }

    private static List<CommentResponse> mapToChildren(CommentEntity comment) {
        List<? extends CommentEntity> results = comment instanceof ScheduleComment
                ? ((ScheduleComment) comment).getChildren()
                : ((ReviewComment) comment).getChildren();
        return results.stream().map(CommentResponse::of).collect(Collectors.toList());
    }
}
