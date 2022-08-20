package teamproject.lam_server.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.service.CommentService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.CREATE_COMMENT;
import static teamproject.lam_server.global.constants.ResponseMessage.READ_COMMENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> writeScheduleComment(
            @PathVariable Long scheduleId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentService.writeScheduleComment(scheduleId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @PostMapping("/schedule/comments/{commentId}")
    public ResponseEntity<?> writeScheduleChildComment(
            @PathVariable Long commentId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentService.writeScheduleChildComment(commentId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<?> writeReviewComment(
            @PathVariable Long reviewId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentService.writeReviewComment(reviewId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @PostMapping("/review/comments/{commentId}")
    public ResponseEntity<?> writeReviewChildComment(
            @PathVariable Long commentId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentService.writeReviewChildComment(commentId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> getScheduleComment(@PathVariable Long scheduleId, PageableDTO pageableDTO) {
        commentService.getScheduleComments(scheduleId, pageableDTO);
        return CustomResponse.success(READ_COMMENT);
    }

}
