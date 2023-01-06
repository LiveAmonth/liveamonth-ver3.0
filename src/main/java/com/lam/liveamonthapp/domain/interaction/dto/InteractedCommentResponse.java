package com.lam.liveamonthapp.domain.interaction.dto;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.entity.InteractionEntity;

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
