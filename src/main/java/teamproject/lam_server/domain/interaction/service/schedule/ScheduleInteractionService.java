package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.InteractionRepository;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;
import teamproject.lam_server.global.dto.response.BooleanResponse;
import teamproject.lam_server.global.service.SecurityContextFinder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleInteractionService implements InteractionService {
    private final SecurityContextFinder finder;
    private final ScheduleLikeRepository scheduleLikeRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.SCHEDULE;
    }

    @Override
    @Transactional
    public void interact(String loginId, Boolean isInteracted, InteractionRequest request) {
        finder.checkLegalLoginId(loginId);
        if (isInteracted) scheduleLikeRepository.cancelLike(request);
        else scheduleLikeRepository.like(request);

    }

    @Override
    public BooleanResponse isLiked(InteractionRequest request) {
        return BooleanResponse.of(interactionRepository.isMemberLikeSchedule(request));
    }
}
