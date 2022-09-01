package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

public interface InteractionService {

    InteractionType getType();

    void react(Boolean likeStatus, InteractionRequest request);

    boolean isLiked(InteractionRequest request);
}
