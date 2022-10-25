package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

public interface InteractionService {

    InteractionType getType();

    void react(String loginId, Boolean isReacted, InteractionRequest request);

    boolean isLiked(InteractionRequest request);
}
