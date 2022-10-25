package teamproject.lam_server.domain.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.dto.ReactedCommentResponse;
import teamproject.lam_server.domain.interaction.service.InteractionServiceFinder;
import teamproject.lam_server.global.dto.response.CustomResponse;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interactions")
public class InteractionApiController {

    private final InteractionServiceFinder interactionServiceFinder;

    @PostMapping("/contents/{type}/{loginId}")
    public ResponseEntity<?> reactContent(
            @PathVariable String type,
            @PathVariable String loginId,
            @RequestParam("is_reacted") Boolean isReacted,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.find(type).react(loginId, isReacted, request);
        return CustomResponse.success(CREATE_INTERACTION);
    }

    @PostMapping("/comments/{commentType}/{loginId}")
    public ResponseEntity<?> reactComment(
            @PathVariable String commentType,
            @PathVariable String loginId,
            @RequestParam(name = "react_type") ReactType reactType,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(commentType).react(loginId, request, reactType);
        return CustomResponse.success(CREATE_INTERACTION);
    }

    @PostMapping("/comments/{type}/{loginId}/cancel")
    public ResponseEntity<?> cancelReactComment(
            @PathVariable String type,
            @PathVariable String loginId,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(type).cancelReact(loginId, request);
        return CustomResponse.success(DELETE_INTERACTION);
    }

    @GetMapping("/member/{type}/liked")
    public ResponseEntity<?> isMemberLikedContent(
            @PathVariable String type,
            InteractionRequest request) {
        boolean result = interactionServiceFinder.find(type).isLiked(request);
        return CustomResponse.success(READ_INTERACTION, result);
    }

    @GetMapping("/member/{memberId}/reacted-comments/{type}")
    public ResponseEntity<?> getMemberReactedComments(
            @PathVariable Long memberId,
            @PathVariable String type,
            @RequestParam List<Long> ids) {
        List<ReactedCommentResponse> result = interactionServiceFinder.findComment(type).getReactedComments(memberId, ids);
        return CustomResponse.success(READ_INTERACTION, result);
    }
}
