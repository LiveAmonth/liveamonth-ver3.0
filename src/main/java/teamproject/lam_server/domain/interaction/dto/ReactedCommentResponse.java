package teamproject.lam_server.domain.interaction.dto;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.entity.schedule.ReactEntity;

@Getter
@Builder
public class ReactedCommentResponse {

    private Long id;
    private ReactType type;

    public static ReactedCommentResponse of(ReactEntity react) {
        return ReactedCommentResponse.builder()
                .id(react.getCommentId())
                .type(react.getType())
                .build();
    }
}
