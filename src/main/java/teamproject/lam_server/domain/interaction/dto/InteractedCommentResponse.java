package teamproject.lam_server.domain.interaction.dto;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.entity.InteractionEntity;

@Getter
@Builder
public class InteractedCommentResponse {

    private Long id;
    private InteractionState state;

    public static InteractedCommentResponse of(InteractionEntity react) {
        return InteractedCommentResponse.builder()
                .id(react.getCommentId())
                .state(react.getState())
                .build();
    }
}
