package com.lam.liveamonthapp.domain.interaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionType;
import com.lam.liveamonthapp.exception.notfound.ServiceNotFound;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InteractionServiceFinder {

    private final List<InteractionService> interactionServices;
    private final List<CommentInteractionService> commentInteractionServices;

    public InteractionService find(InteractionType type) {
        return interactionServices.stream()
                .filter(interactionService -> interactionService.getType() == type)
                .findAny()
                .orElseThrow(ServiceNotFound::new);
    }

    public CommentInteractionService findComment(CommentType type) {
        return commentInteractionServices.stream()
                .filter(commentInteractionService -> commentInteractionService.getType() == type)
                .findAny()
                .orElseThrow(ServiceNotFound::new);
    }
}
