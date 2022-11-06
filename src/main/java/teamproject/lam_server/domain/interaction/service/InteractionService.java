package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.global.dto.response.BooleanResponse;

public interface InteractionService {

    InteractionType getType();

    void interact(String loginId, Boolean isInteracted, InteractionRequest request);

    BooleanResponse isLiked(InteractionRequest request);
}
