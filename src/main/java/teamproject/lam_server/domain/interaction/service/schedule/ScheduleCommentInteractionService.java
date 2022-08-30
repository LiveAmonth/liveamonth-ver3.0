package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleCommentReactRepository;
import teamproject.lam_server.domain.interaction.service.CommentInteractionService;
import teamproject.lam_server.exception.badrequest.AlreadyDislikeComment;
import teamproject.lam_server.exception.badrequest.AlreadyLikeComment;

import static teamproject.lam_server.domain.interaction.constants.ReactType.LIKE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleCommentInteractionService implements CommentInteractionService {

    private final ScheduleCommentReactRepository reactRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.SCHEDULE;
    }

    @Override
    @Transactional
    public void react(InteractionRequest request, ReactType type) {
        checkExists(request);
        reactRepository.react(request, type);
    }

    @Override
    @Transactional
    public void cancelReact(InteractionRequest request, ReactType type) {
        reactRepository.cancelReact(request, type);
    }

    private void checkExists(InteractionRequest request) {
        reactRepository.existsReact(request).ifPresent(reactType -> {
            throw reactType == LIKE
                    ? new AlreadyLikeComment()
                    : new AlreadyDislikeComment();
        });
    }
}