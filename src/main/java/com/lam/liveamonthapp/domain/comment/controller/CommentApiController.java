package com.lam.liveamonthapp.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentEdit;
import com.lam.liveamonthapp.domain.comment.dto.response.BestCommentResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentResponse;
import com.lam.liveamonthapp.domain.comment.service.CommentServiceFinder;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import javax.validation.Valid;
import java.util.List;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;

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
        commentServiceFinder.find(type).writeComment(contentId, request);
        return CustomResponse.success(CREATE_COMMENT);
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
