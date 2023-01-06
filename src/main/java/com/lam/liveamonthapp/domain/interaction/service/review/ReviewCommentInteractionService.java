package com.lam.liveamonthapp.domain.interaction.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.dto.InteractedCommentResponse;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.repository.review.ReviewCommentInteractionRepository;
import com.lam.liveamonthapp.domain.interaction.service.CommentInteractionService;
import com.lam.liveamonthapp.exception.badrequest.AlreadyDislikeComment;
import com.lam.liveamonthapp.exception.badrequest.AlreadyLikeComment;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;

import java.util.List;
import java.util.stream.Collectors;

import static com.lam.liveamonthapp.domain.interaction.constants.InteractionState.LIKE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentInteractionService implements CommentInteractionService {

    private final ReviewCommentInteractionRepository interactionRepository;
    private final SecurityContextFinder finder;

    @Override
    public CommentType getType() {
        return CommentType.REVIEW;
    }


    @Override
    @Transactional
    public void interact(String loginId, InteractionRequest request, InteractionState state) {
        finder.checkLegalLoginId(loginId);

        checkExists(request);
        interactionRepository.interact(request, state);
    }

    @Override
    @Transactional
    public void cancelInteraction(String loginId, InteractionRequest request) {
        interactionRepository.cancelInteraction(request);
    }

    @Override
    public List<InteractedCommentResponse> getInteractedComments(Long memberId, List<Long> ids) {
        return interactionRepository.getInteractedComments(memberId, ids).stream()
                .map(InteractedCommentResponse::of)
                .collect(Collectors.toList());
    }

    private void checkExists(InteractionRequest request) {
        interactionRepository.existsInteraction(request).ifPresent(interactionState -> {
            throw interactionState == LIKE
                    ? new AlreadyLikeComment()
                    : new AlreadyDislikeComment();
        });
    }
}
