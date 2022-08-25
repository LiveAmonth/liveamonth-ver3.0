package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleInteractionService implements InteractionService {

    private final ScheduleLikeRepository scheduleLikeRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.SCHEDULE;
    }

    @Override
    public void like(InteractionRequest request) {
        scheduleLikeRepository.like(request);
    }

    @Override
    public void cancelLike(InteractionRequest request) {
        scheduleLikeRepository.cancelLike(request);
    }
}
