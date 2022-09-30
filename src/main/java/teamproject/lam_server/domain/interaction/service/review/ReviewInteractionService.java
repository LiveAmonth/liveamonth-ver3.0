package teamproject.lam_server.domain.interaction.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.InteractionRepository;
import teamproject.lam_server.domain.interaction.repository.review.ReviewLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;
import teamproject.lam_server.global.service.SecurityContextFinder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewInteractionService implements InteractionService {
    private final SecurityContextFinder finder;
    private final ReviewLikeRepository reviewLikeRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.REVIEW;
    }

    @Override
    @Transactional
    public void react(Boolean likeStatus, InteractionRequest request) {
        finder.checkLegalWriterId(request.getFrom());

        if (likeStatus) reviewLikeRepository.cancelLike(request);
        else reviewLikeRepository.like(request);
    }

    @Override
    public boolean isLiked(InteractionRequest request) {
        return interactionRepository.isMemberLikeReview(request);
    }
}
