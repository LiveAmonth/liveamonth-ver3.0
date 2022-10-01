package teamproject.lam_server.domain.interaction.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.InteractionRepository;
import teamproject.lam_server.domain.interaction.repository.member.FollowRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;
import teamproject.lam_server.global.service.SecurityContextFinder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInteractionService implements InteractionService {
    private final SecurityContextFinder finder;
    private final FollowRepository followRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.MEMBER;
    }

    @Override
    @Transactional
    public void react(Boolean likeStatus, InteractionRequest request) {
        finder.checkLegalWriterId(request.getFrom());
        if (likeStatus) followRepository.follow(finder.getLoggedInMemberLoginId(), request);
        else followRepository.unFollow(request);
    }

    @Override
    public boolean isLiked(InteractionRequest request) {
        return interactionRepository.isMemberFollow(request);
    }
}
