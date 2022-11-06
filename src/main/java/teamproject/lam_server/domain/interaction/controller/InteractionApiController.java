package teamproject.lam_server.domain.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.dto.InteractedCommentResponse;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.service.InteractionServiceFinder;
import teamproject.lam_server.global.dto.response.CustomResponse;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interactions")
public class
InteractionApiController {

    private final InteractionServiceFinder interactionServiceFinder;

    @PostMapping("/contents/{type}/{loginId}")
    public ResponseEntity<?> interact(
            @PathVariable String type,
            @PathVariable String loginId,
            @RequestParam("is_interacted") Boolean isInteracted,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.find(type).interact(loginId, isInteracted, request);
        return CustomResponse.success(CREATE_INTERACTION);
    }

    @PostMapping("/comments/{commentType}/{loginId}")
    public ResponseEntity<?> interactComment(
            @PathVariable String commentType,
            @PathVariable String loginId,
            @RequestParam(name = "interaction_state") InteractionState interactionState,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(commentType).interact(loginId, request, interactionState);
        return CustomResponse.success(CREATE_INTERACTION);
    }

    @PostMapping("/comments/{type}/{loginId}/cancel")
    public ResponseEntity<?> cancelInteractComment(
            @PathVariable String type,
            @PathVariable String loginId,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(type).cancelInteraction(loginId, request);
        return CustomResponse.success(DELETE_INTERACTION);
    }

    @GetMapping("/member/{type}/liked")
    public ResponseEntity<?> isMemberLikedContent(
            @PathVariable String type,
            InteractionRequest request) {
        boolean result = interactionServiceFinder.find(type).isLiked(request);
        return CustomResponse.success(READ_INTERACTION, result);
    }

    @GetMapping("/member/{memberId}/interacted-comments/{type}")
    public ResponseEntity<?> getInteractedCommentsByMember(
            @PathVariable Long memberId,
            @PathVariable String type,
            @RequestParam List<Long> ids) {
        List<InteractedCommentResponse> result = interactionServiceFinder.findComment(type).getInteractedComments(memberId, ids);
        return CustomResponse.success(READ_INTERACTION, result);
    }
}
