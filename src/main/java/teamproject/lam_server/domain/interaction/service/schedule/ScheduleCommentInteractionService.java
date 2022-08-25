package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleCommentDislikeRepository;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleCommentLikeRepository;
import teamproject.lam_server.domain.interaction.service.CommentInteractionService;
import teamproject.lam_server.exception.badrequest.AlreadyDislikeComment;
import teamproject.lam_server.exception.badrequest.AlreadyLikeComment;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleCommentInteractionService implements CommentInteractionService {

    private final ScheduleCommentLikeRepository scheduleCommentLikeRepository;
    private final ScheduleCommentDislikeRepository scheduleCommentDislikeRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.SCHEDULE_COMMENT;
    }

    @Override
    public void like(InteractionRequest request) {
        checkExists(request);
        scheduleCommentLikeRepository.like(request);
    }

    @Override
    public void cancelLike(InteractionRequest request) {
        scheduleCommentLikeRepository.cancelLike(request);
    }

    @Override
    public void dislike(InteractionRequest request) {
        checkExists(request);
        scheduleCommentDislikeRepository.dislike(request);
    }

    @Override
    public void cancelDislike(InteractionRequest request) {
        scheduleCommentDislikeRepository.cancelDislike(request);
    }

    private void checkExists(InteractionRequest request) {
        // 이미 추천한 경우
        if (scheduleCommentLikeRepository.existsLike(request)) {
            throw new AlreadyLikeComment();
        }
        // 이미 비추천한 경우
        if (scheduleCommentDislikeRepository.existsDislike(request))
            throw new AlreadyDislikeComment();
    }
}
