package teamproject.lam_server.domain.interaction.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.service.CommentInteractionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentInteractionService implements CommentInteractionService {

    @Override
    public InteractionType getType() {
        return InteractionType.REVIEW_COMMENT;
    }


    @Override
    public void like(InteractionRequest request) {

    }

    @Override
    public void cancelLike(InteractionRequest request) {

    }

    @Override
    @Transactional
    public void dislike(InteractionRequest request) {

    }

    @Override
    public void cancelDislike(InteractionRequest request) {

    }

    private void checkExists(InteractionRequest request) {

    }
}
