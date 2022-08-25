package teamproject.lam_server.domain.interaction.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.service.InteractionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewInteractionService implements InteractionService {
    @Override
    public InteractionType getType() {
        return InteractionType.REVIEW;
    }

    @Override
    public void like(InteractionRequest request) {

    }

    @Override
    public void cancelLike(InteractionRequest request) {

    }
}
