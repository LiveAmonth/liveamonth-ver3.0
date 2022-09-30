package teamproject.lam_server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.CommentEditor;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public abstract class CommentService{

    protected final SecurityContextFinder finder;

    protected CommentResponse mapToCommentResponse(CommentResponse.CommentResponseBuilder builder, Long parentId, List<ScheduleComment> children) {
        return builder.commentReplies(
                children.stream()
                        .filter(comment -> comment.getParent().getId().equals(parentId))
                        .map(CommentReplyResponse::of).collect(Collectors.toList())
        ).build();
    }

    public abstract CommentType getType();
    public abstract void writeComment(CommentCreate request);
    public abstract void editComment(Long commentId, CommentEdit request);
    public abstract void deleteComment(Long commentId);
    public abstract CustomPage<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO);
    public abstract CommentResponse getBestComments(Long contentId);

    @Transactional
    protected <T extends CommentEntity> void edit(T t, CommentEdit request){
        finder.checkLegalWriterOfPost(t);

        CommentEditor editor = t.toEditor()
                .comment(request.getComment())
                .build();

        t.edit(editor);
    }
}
