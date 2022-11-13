package teamproject.lam_server.domain.comment.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreate {

    @NotEmpty
    @Length(max = 1000)
    private String comment;

    private Long parentId;

    @Builder
    public CommentCreate(String comment, Long parentId) {
        this.comment = comment;
        if (parentId != null) {
            this.parentId = parentId;
        }
    }
}
