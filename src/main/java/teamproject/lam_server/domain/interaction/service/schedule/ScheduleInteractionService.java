package teamproject.lam_server.domain.interaction.service.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleInteractionService implements InteractionService {

    private final ScheduleLikeRepository scheduleLikeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public InteractionType getType() {
        return InteractionType.SCHEDULE;
    }

    @Override
    @Transactional
    public void like(InteractionRequest request) {
        scheduleLikeRepository.like(request);
    }

    @Override
    @Transactional
    public void cancelLike(InteractionRequest request) {
        scheduleLikeRepository.cancelLike(request);
    }

    @Override
    public boolean isLike(String accessToken, Long contentId) {
        String loginId = jwtTokenProvider.getAuthentication(accessToken).getName();
        return scheduleLikeRepository.isMemberLike(loginId, contentId);
    }
}
