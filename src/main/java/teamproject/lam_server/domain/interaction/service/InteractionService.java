package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

public interface InteractionService {

    InteractionType getType();

    void like(InteractionRequest request);

    void cancelLike(InteractionRequest request);

    boolean isLike(InteractionRequest request);
}
