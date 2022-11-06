package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

public interface InteractionService {

    InteractionType getType();

    void interact(String loginId, Boolean isInteracted, InteractionRequest request);

    boolean isLiked(InteractionRequest request);
}
