package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.InteractionRepository;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ScheduleInteractionService implements InteractionService {

    private final ScheduleLikeRepository scheduleLikeRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.SCHEDULE;
    }

    @Override
    @Transactional
    public void react(Boolean likeStatus, InteractionRequest request) {
        if (likeStatus) scheduleLikeRepository.cancelLike(request);
        else scheduleLikeRepository.like(request);

    }

    @Override
    public boolean isLiked(InteractionRequest request) {
        return interactionRepository.isMemberLikeSchedule(request);
    }
}
