package com.lam.liveamonthapp.domain.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionType;
import com.lam.liveamonthapp.domain.interaction.dto.InteractedCommentResponse;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.service.InteractionServiceFinder;
import com.lam.liveamonthapp.global.dto.response.BooleanCheckResponse;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;

import javax.validation.Valid;
import java.util.List;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interactions")
public class InteractionApiController {

    private final InteractionServiceFinder interactionServiceFinder;

    @PostMapping("/contents/{type}/{login_id}")
    public ResponseEntity<?> interact(
            @PathVariable InteractionType type,
            @PathVariable("login_id") String loginId,
            @RequestParam("is_interacted") Boolean isInteracted,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.find(type).interact(loginId, isInteracted, request);
        return CustomResponse.success(CREATE_INTERACTION);
    }

    @PostMapping("/comments/{comment_type}/{login_id}")
    public ResponseEntity<?> interactComment(
            @PathVariable("comment_type") CommentType commentType,
            @PathVariable("login_id") String loginId,
            @RequestParam("interaction_state") InteractionState interactionState,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(commentType).interact(loginId, request, interactionState);
        return CustomResponse.success(CREATE_INTERACTION);
    }

    @PostMapping("/comments/{comment_type}/{loginId}/cancel")
    public ResponseEntity<?> cancelInteractComment(
            @PathVariable("comment_type") CommentType commentType,
            @PathVariable String loginId,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(commentType).cancelInteraction(loginId, request);
        return CustomResponse.success(DELETE_INTERACTION);
    }

    @GetMapping("/member/{type}/liked")
    public ResponseEntity<?> isMemberInteractObject(
            @PathVariable InteractionType type,
            InteractionRequest request) {
        BooleanCheckResponse result = interactionServiceFinder.find(type).isInteracted(request);
        return CustomResponse.success(READ_INTERACTION, result);
    }

    @GetMapping("/member/{member_id}/interacted-comments/{comment_type}")
    public ResponseEntity<?> getInteractedCommentsByMember(
            @PathVariable("member_id") Long memberId,
            @PathVariable("comment_type") CommentType commentType,
            @RequestParam List<Long> ids) {
        List<InteractedCommentResponse> result = interactionServiceFinder.findComment(commentType).getInteractedComments(memberId, ids);
        return CustomResponse.success(READ_INTERACTION, result);
    }
}
