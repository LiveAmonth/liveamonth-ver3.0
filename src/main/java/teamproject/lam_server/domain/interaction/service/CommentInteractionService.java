package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

public interface CommentInteractionService extends InteractionService {
    void dislike(InteractionRequest request);

    void cancelDislike(InteractionRequest request);

}
