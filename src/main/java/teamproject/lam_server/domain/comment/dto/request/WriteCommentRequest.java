package teamproject.lam_server.domain.comment.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.member.entity.Member;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class WriteCommentRequest {
    @NotEmpty
    @Length(max = 1000)
    private String comment;

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