package teamproject.lam_server.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
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
            @RequestParam(required = false, defaultValue = "0") Long commentId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentService.writeScheduleComment(scheduleId, commentId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<?> writeReviewComment(
            @PathVariable Long reviewId,
            @RequestParam(required = false, defaultValue = "0") Long commentId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentService.writeReviewComment(reviewId, commentId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<?> getScheduleComment(@PathVariable Long scheduleId, PageableDTO pageableDTO) {
        Page<CommentResponse> result = commentService.getScheduleComments(scheduleId, pageableDTO);
        return CustomResponse.success(READ_COMMENT, result);
    }

}
