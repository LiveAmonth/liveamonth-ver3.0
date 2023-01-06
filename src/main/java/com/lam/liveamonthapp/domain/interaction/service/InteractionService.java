package com.lam.liveamonthapp.domain.interaction.service;

import com.lam.liveamonthapp.domain.interaction.constants.InteractionType;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.global.dto.response.BooleanCheckResponse;

public interface InteractionService {

    InteractionType getType();

    void interact(String loginId, Boolean isInteracted, InteractionRequest request);

    BooleanCheckResponse isInteracted(InteractionRequest request);
}
