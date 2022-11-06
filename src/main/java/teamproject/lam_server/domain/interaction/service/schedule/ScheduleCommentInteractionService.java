package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.dto.InteractedCommentResponse;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleCommentInteractionRepository;
import teamproject.lam_server.domain.interaction.service.CommentInteractionService;
import teamproject.lam_server.exception.badrequest.AlreadyDislikeComment;
import teamproject.lam_server.exception.badrequest.AlreadyLikeComment;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.util.List;
import java.util.stream.Collectors;

import static teamproject.lam_server.domain.interaction.constants.InteractionState.LIKE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleCommentInteractionService implements CommentInteractionService {

    private final ScheduleCommentInteractionRepository interactionRepository;
    private final SecurityContextFinder finder;

    @Override
    public CommentType getType() {
        return CommentType.SCHEDULE;
    }

    @Override
    @Transactional
    public void interact(String loginId, InteractionRequest request, InteractionState state) {
        finder.checkLegalLoginId(loginId);

        checkExists(request);
        interactionRepository.interact(request, state);
    }

    @Override
    @Transactional
    public void cancelInteraction(String loginId, InteractionRequest request) {
        interactionRepository.cancelInteraction(request);
    }

    @Override
    public List<InteractedCommentResponse> getInteractedComments(Long memberId, List<Long> ids) {
        return interactionRepository.getInteractedComments(memberId, ids).stream()
                .map(InteractedCommentResponse::of)
                .collect(Collectors.toList());
    }

    private void checkExists(InteractionRequest request) {
        interactionRepository.existsInteraction(request).ifPresent(interactionState -> {
            throw interactionState == LIKE
                    ? new AlreadyLikeComment()
                    : new AlreadyDislikeComment();
        });
    }
}
