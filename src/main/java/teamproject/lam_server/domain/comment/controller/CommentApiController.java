package teamproject.lam_server.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.service.CommentServiceFinder;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentApiController {

    private final CommentServiceFinder commentServiceFinder;

    @PostMapping("/{type}")
    public ResponseEntity<?> writeComment(
            @PathVariable String type,
            @RequestBody @Valid CommentCreate request) {
        commentServiceFinder.find(type).writeComment(request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @PatchMapping("/{type}/{commentId}")
    public ResponseEntity<?> editComment(
            @PathVariable String type,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentEdit request) {
        commentServiceFinder.find(type).editComment(commentId, request);
        return CustomResponse.success(UPDATE_COMMENT);
    }

    @DeleteMapping("/{type}/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable String type,
            @PathVariable Long commentId) {
        commentServiceFinder.find(type).deleteComment(commentId);
        return CustomResponse.success(DELETE_COMMENT);
    }

    @GetMapping("/{type}/{content_id}")
    public ResponseEntity<?> getComments(
            @PathVariable String type,
            @PathVariable("content_id") Long contentId,
            PageableDTO pageableDTO) {
        CustomPage<CommentResponse> result =
                commentServiceFinder.find(type).getComments(contentId, pageableDTO);

        return CustomResponse.success(READ_COMMENT, result);
    }

}
