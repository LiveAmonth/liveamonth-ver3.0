package teamproject.lam_server.domain.interaction.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.InteractionRepository;
import teamproject.lam_server.domain.interaction.repository.review.ReviewLikeRepository;
import teamproject.lam_server.domain.interaction.service.InteractionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewInteractionService implements InteractionService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final InteractionRepository interactionRepository;
    @Override
    public InteractionType getType() {
        return InteractionType.REVIEW;
    }

    @Override
    @Transactional
    public void like(InteractionRequest request) {
        reviewLikeRepository.like(request);
    }

    @Override
    @Transactional
    public void cancelLike(InteractionRequest request) {
        reviewLikeRepository.cancelLike(request);
    }

    @Override
    public boolean isLike(InteractionRequest request) {
        return interactionRepository.isMemberLikeReview(request);
    }
}
