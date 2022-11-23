package teamproject.lam_server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.BestCommentResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.CommentEditor;
import teamproject.lam_server.domain.comment.entity.CommentEntity;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class CommentService{
    protected final SecurityContextFinder finder;

    public abstract CommentType getType();
    public abstract void writeComment(Long contentId, CommentCreate request);
    public abstract void editComment(Long commentId, CommentEdit request);
    public abstract void deleteComment(Long commentId);
    public abstract CustomPage<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO);

    @Transactional
    protected <T extends CommentEntity> void edit(T t, CommentEdit request){
        finder.checkLegalWriterOfPost(t);

        CommentEditor editor = t.toEditor()
                .comment(request.getComment())
                .build();

        t.edit(editor);
    }

    public abstract List<BestCommentResponse> getBestComments(Long contentId);
}
