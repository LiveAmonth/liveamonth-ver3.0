package teamproject.lam_server.domain.interaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.exception.notfound.ServiceNotFound;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InteractionServiceFinder {

    private final List<InteractionService> interactionServices;
    private final List<CommentInteractionService> commentInteractionServices;

    public InteractionService find(String type) {
        return interactionServices.stream()
                .filter(interactionService -> interactionService.getType() == InteractionType.valueOf(type.toUpperCase()))
                .findAny()
                .orElseThrow(ServiceNotFound::new);
    }

    public CommentInteractionService findComment(String type) {
        return commentInteractionServices.stream()
                .filter(commentInteractionService -> commentInteractionService.getType() == InteractionType.valueOf(type.toUpperCase()))
                .findAny()
                .orElseThrow(ServiceNotFound::new);
    }
}
