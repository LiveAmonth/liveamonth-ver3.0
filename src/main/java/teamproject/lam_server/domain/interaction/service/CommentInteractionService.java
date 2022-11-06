package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.dto.InteractedCommentResponse;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

import java.util.List;

public interface CommentInteractionService {

    CommentType getType();

    void interact(String loginId, InteractionRequest request, InteractionState state);

    void cancelInteraction(String loginId, InteractionRequest request);

    List<InteractedCommentResponse> getInteractedComments(Long memberId, List<Long> request);
}
