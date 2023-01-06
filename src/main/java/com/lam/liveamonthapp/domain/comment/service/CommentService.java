package com.lam.liveamonthapp.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentEdit;
import com.lam.liveamonthapp.domain.comment.dto.response.BestCommentResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentResponse;
import com.lam.liveamonthapp.domain.comment.entity.CommentEditor;
import com.lam.liveamonthapp.domain.comment.entity.CommentEntity;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import java.util.List;

@RequiredArgsConstructor
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
