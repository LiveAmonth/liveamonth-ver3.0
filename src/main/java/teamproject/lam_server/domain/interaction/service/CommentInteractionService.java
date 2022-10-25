package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.dto.ReactedCommentResponse;

import java.util.List;

public interface CommentInteractionService {

    InteractionType getType();

    void react(String loginId, InteractionRequest request, ReactType type);

    void cancelReact(String loginId, InteractionRequest request);

    List<ReactedCommentResponse> getReactedComments(Long memberId, List<Long> request);
}
