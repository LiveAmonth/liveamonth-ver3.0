package teamproject.lam_server.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.comment.dto.response.CommentProfileResponse;
import teamproject.lam_server.domain.comment.entity.CommentEntity;

import java.util.List;

import static teamproject.lam_server.util.DateTimeUtil.calcTimeBefore;

@Getter
@Builder
public class CommentResponse {

    private String content;
    private CommentProfileResponse profile;
    private List<CommentResponse> commentReply;
    private String writeTimeBefore;

    public static CommentResponse of(CommentEntity comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .profile(CommentProfileResponse.of(comment.getMember()))
                .writeTimeBefore(calcTimeBefore(comment.getCreatedDate()))
                .build();
    }
}
