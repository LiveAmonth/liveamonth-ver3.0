package teamproject.lam_server.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.service.CommentServiceFinder;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.CREATE_COMMENT;
import static teamproject.lam_server.global.constants.ResponseMessage.READ_COMMENT;
import static teamproject.lam_server.util.JwtUtil.extractAccessToken;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentApiController {

    private final CommentServiceFinder commentServiceFinder;

    @PostMapping("/{contentId}")
    public ResponseEntity<?> writeComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Long contentId,
            @RequestParam CommentType type,
            @RequestParam(name = "comment_id", required = false) Long commentId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentServiceFinder.find(type)
                .writeComment(extractAccessToken(token), contentId, commentId != 0 ? commentId : null, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> getComments(
            @PathVariable Long contentId,
            @RequestParam CommentType type,
            PageableDTO pageableDTO) {

        Page<CommentResponse> result =
                commentServiceFinder.find(type).getComments(contentId, pageableDTO);

        return CustomResponse.success(READ_COMMENT, result);
    }

}
