package teamproject.lam_server.domain.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
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

    @PostMapping("/comments/like")
    public ResponseEntity<?> likeComment(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(type).like(request);
        return CustomResponse.success();
    }

    @PostMapping("/comments/dislike")
    public ResponseEntity<?> disLikeComment(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(type).dislike(request);
        return CustomResponse.success();
    }

    @PostMapping("/like/cancel")
    public ResponseEntity<?> cancelLikeContent(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.find(type).cancelLike(request);
        return CustomResponse.success();
    }

    @PostMapping("/comments/like/cancel")
    public ResponseEntity<?> cancelLikeComment(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(type).cancelLike(request);
        return CustomResponse.success();
    }

    @PostMapping("/comments/dislike/cancel")
    public ResponseEntity<?> cancelDisLikeComment(
            @RequestParam InteractionType type,
            @RequestBody @Valid InteractionRequest request) {
        interactionServiceFinder.findComment(type).cancelDislike(request);
        return CustomResponse.success();
    }

}
