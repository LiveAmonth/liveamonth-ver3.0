package teamproject.lam_server.domain.comment.service;

import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentEditor;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class CommentService{

    protected CommentResponse mapToCommentResponse(CommentResponse.CommentResponseBuilder builder, Long parentId, List<ScheduleComment> children) {
        return builder.commentReplies(
                children.stream()
                        .filter(comment -> comment.getParent().getId().equals(parentId))
                        .map(CommentReplyResponse::of).collect(Collectors.toList())
        ).build();
    }

    abstract CommentType getType();

    public abstract CustomPage<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO);

    public abstract void writeComment(CommentEditor request);

    public abstract CommentResponse getBestComments(Long contentId);
}
