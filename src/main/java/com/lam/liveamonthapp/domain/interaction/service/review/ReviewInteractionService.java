package com.lam.liveamonthapp.domain.interaction.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionType;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.repository.InteractionQueryRepository;
import com.lam.liveamonthapp.domain.interaction.repository.review.ReviewLikeRepository;
import com.lam.liveamonthapp.domain.interaction.service.InteractionService;
import com.lam.liveamonthapp.global.dto.response.BooleanCheckResponse;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.INTERACTED_OBJECT;
import static com.lam.liveamonthapp.global.constants.ResponseMessage.NOT_INTERACTED_OBJECT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewInteractionService implements InteractionService {
    private final SecurityContextFinder finder;
    private final ReviewLikeRepository reviewLikeRepository;
    private final InteractionQueryRepository interactionQueryRepository;

    @Override
    public InteractionType getType() {
        return InteractionType.REVIEW;
    }

    @Override
    @Transactional
    public void interact(String loginId, Boolean isInteracted, InteractionRequest request) {
        finder.checkLegalLoginId(loginId);

        if (isInteracted) reviewLikeRepository.cancelLike(request);
        else reviewLikeRepository.like(request);
    }

    @Override
    public BooleanCheckResponse isInteracted(InteractionRequest request) {
        return interactionQueryRepository.isMemberLikeReview(request)
                ? BooleanCheckResponse.of(true, INTERACTED_OBJECT)
                : BooleanCheckResponse.of(false, NOT_INTERACTED_OBJECT);
    }
}
