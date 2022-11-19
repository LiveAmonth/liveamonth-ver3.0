package teamproject.lam_server.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.BestCommentResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.service.CommentServiceFinder;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentServiceFinder commentServiceFinder;

    @PostMapping("/{type}/{content_id}")
    public ResponseEntity<?> writeComment(
            @PathVariable CommentType type,
            @PathVariable("content_id") Long contentId,
            @RequestBody @Valid CommentCreate request) {
        PostIdResponse result = commentServiceFinder.find(type).writeComment(contentId, request);
        return CustomResponse.success(CREATE_COMMENT, result);
    }

    @PatchMapping("/{type}/{comment_id}")
    public ResponseEntity<?> editComment(
            @PathVariable CommentType type,
            @PathVariable("comment_id") Long commentId,
            @RequestBody @Valid CommentEdit request) {
        commentServiceFinder.find(type).editComment(commentId, request);
        return CustomResponse.success(UPDATE_COMMENT);
    }

    @DeleteMapping("/{type}/{comment_id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable CommentType type,
            @PathVariable("comment_id") Long commentId) {
        commentServiceFinder.find(type).deleteComment(commentId);
        return CustomResponse.success(DELETE_COMMENT);
    }

    @GetMapping("/{type}/{content_id}")
    public ResponseEntity<?> getComments(
            @PathVariable CommentType type,
            @PathVariable("content_id") Long contentId,
            PageableDTO pageableDTO) {
        CustomPage<CommentResponse> result =
                commentServiceFinder.find(type).getComments(contentId, pageableDTO);
        return CustomResponse.success(READ_COMMENT, result);
    }

    @GetMapping("/{type}/{content_id}/best")
    public ResponseEntity<?> getBestComments(
            @PathVariable CommentType type,
            @PathVariable("content_id") Long contentId) {
        List<BestCommentResponse> result =
                commentServiceFinder.find(type).getBestComments(contentId);
        return CustomResponse.success(READ_COMMENT, result);
    }
}
