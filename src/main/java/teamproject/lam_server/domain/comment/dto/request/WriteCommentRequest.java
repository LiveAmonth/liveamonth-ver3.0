package teamproject.lam_server.domain.comment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.member.entity.Member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WriteCommentRequest {
    @NotEmpty
    @Length(max = 1000)
    private String comment;

    @NotNull
    private Long contentId;

    private Long commentId;

    public ScheduleComment.ScheduleCommentBuilder toScheduleEntity(Member member) {
        return ScheduleComment.builder()
                .content(comment)
                .member(member);
    }

    public ReviewComment.ReviewCommentBuilder toReviewEntity(Member member) {
        return ReviewComment.builder()
                .content(comment)
                .member(member);
    }
}
