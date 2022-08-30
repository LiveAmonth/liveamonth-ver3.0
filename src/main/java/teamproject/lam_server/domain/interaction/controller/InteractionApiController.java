package teamproject.lam_server.domain.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.service.InteractionServiceFinder;
import teamproject.lam_server.global.dto.CustomResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interactions")
public class InteractionApiController {

    private final InteractionServiceFinder interactionServiceFinder;

    @PostMapping("/like")
    public ResponseEntity<?> likeContent(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.find(type).like(request);
        return CustomResponse.success();
    }

    @PostMapping("/like/cancel")
    public ResponseEntity<?> cancelLikeContent(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.find(type).cancelLike(request);
        return CustomResponse.success();
    }

    @PostMapping("/comments/react")
    public ResponseEntity<?> reactComment(
            @RequestParam(name = "comment_type") InteractionType commentType,
            @RequestParam(name = "react_type") ReactType reactType,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(commentType).react(request, reactType);
        return CustomResponse.success();
    }

    @PostMapping("/comments/react/cancel")
    public ResponseEntity<?> cancelReactComment(
            @RequestParam(name = "comment_type") InteractionType commentType,
            @RequestParam(name = "react_type") ReactType reactType,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(commentType).cancelReact(request, reactType);
        return CustomResponse.success();
    }

    @GetMapping("/member/likes")
    public ResponseEntity<?> isMemberLikeContent(@RequestParam InteractionType type, InteractionRequest request) {
        boolean result = interactionServiceFinder.find(type).isLike(request);
        return CustomResponse.success(result);
    }

    @GetMapping("/member/like-comments")
    public ResponseEntity<?> getMemberLikeComments(@RequestParam InteractionType type, InteractionRequest request) {
        boolean result = interactionServiceFinder.find(type).isLike(request);
        return CustomResponse.success(result);
    }
}
