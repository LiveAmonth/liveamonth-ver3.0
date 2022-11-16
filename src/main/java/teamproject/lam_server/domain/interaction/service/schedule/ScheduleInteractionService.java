package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.InteractionQueryRepository;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;
import teamproject.lam_server.global.dto.response.BooleanCheckResponse;
import teamproject.lam_server.global.service.SecurityContextFinder;

import static teamproject.lam_server.global.constants.ResponseMessage.INTERACTED_OBJECT;
import static teamproject.lam_server.global.constants.ResponseMessage.NOT_INTERACTED_OBJECT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleInteractionService implements InteractionService {
    private final SecurityContextFinder finder;
    private final ScheduleLikeRepository scheduleLikeRepository;
    private final InteractionQueryRepository interactionQueryRepository;

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
    public BooleanCheckResponse isInteracted(InteractionRequest request) {
        return interactionQueryRepository.isMemberLikeSchedule(request)
                ? BooleanCheckResponse.of(true, INTERACTED_OBJECT)
                : BooleanCheckResponse.of(false, NOT_INTERACTED_OBJECT);
    }
}
