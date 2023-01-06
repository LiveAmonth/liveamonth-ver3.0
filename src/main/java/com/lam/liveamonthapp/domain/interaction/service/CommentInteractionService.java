package com.lam.liveamonthapp.domain.interaction.service;

import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.dto.InteractedCommentResponse;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;

import java.util.List;

public interface CommentInteractionService {

    CommentType getType();

    void interact(String loginId, InteractionRequest request, InteractionState state);

    void cancelInteraction(String loginId, InteractionRequest request);

    List<InteractedCommentResponse> getInteractedComments(Long memberId, List<Long> request);
}
