package teamproject.lam_server.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.service.CommentServiceFinder;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.CREATE_COMMENT;
import static teamproject.lam_server.global.constants.ResponseMessage.READ_COMMENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentApiController {

    private final CommentServiceFinder commentServiceFinder;

    @PostMapping("/{type}")
    public ResponseEntity<?> writeComment(
            @PathVariable String type,
            @RequestParam("login_id") String loginId,
            @RequestBody @Valid WriteCommentRequest request) {
        commentServiceFinder.find(type)
                .writeComment(loginId, request);
        return CustomResponse.success(CREATE_COMMENT);
    }

    @GetMapping("/{type}/{contentId}")
    public ResponseEntity<?> getComments(
            @PathVariable Long contentId,
            @PathVariable String type,
            PageableDTO pageableDTO) {

        Page<CommentResponse> result =
                commentServiceFinder.find(type).getComments(contentId, pageableDTO);

        return CustomResponse.success(READ_COMMENT, result);
    }

}
