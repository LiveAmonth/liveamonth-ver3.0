package com.lam.liveamonthapp.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentEdit;
import com.lam.liveamonthapp.domain.comment.dto.response.BestCommentResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentResponse;
import com.lam.liveamonthapp.domain.comment.entity.ReviewComment;
import com.lam.liveamonthapp.domain.comment.repository.ReviewCommentQueryRepository;
import com.lam.liveamonthapp.domain.comment.repository.ReviewCommentRepository;
import com.lam.liveamonthapp.exception.notfound.CommentNotFound;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewCommentService extends CommentService {
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewCommentQueryRepository reviewCommentQueryRepository;

    public ReviewCommentService(
            SecurityContextFinder finder,
            ReviewCommentRepository reviewCommentRepository,
            ReviewCommentQueryRepository reviewCommentQueryRepository) {
        super(finder);
        this.reviewCommentRepository = reviewCommentRepository;
        this.reviewCommentQueryRepository = reviewCommentQueryRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.REVIEW;
    }

    @Override
    @Transactional
    public void writeComment(Long contentId, CommentCreate request) {
        reviewCommentRepository.write(
                finder.getAuthenticationName(), finder.getLoggedInMemberId(), contentId, request
        );
    }

    @Override
    @Transactional
    public void editComment(Long commentId, CommentEdit request) {
        super.edit(
                reviewCommentRepository
                        .findById(commentId)
                        .orElseThrow(CommentNotFound::new),
                request
        );
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        ReviewComment comment = reviewCommentRepository
                .findById(commentId)
                .orElseThrow(CommentNotFound::new);
        finder.checkLegalWriterOfPost(comment);
        reviewCommentRepository.delete(comment);
    }

    @Override
    public CustomPage<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        Page<CommentResponse> reviewComments = reviewCommentQueryRepository.getComments(contentId, pageable);

        return CustomPage.<CommentResponse>builder()
                .page(reviewComments)
                .build();
    }

    @Override
    public List<BestCommentResponse> getBestComments(Long contentId) {
        return reviewCommentQueryRepository.getBestComments(contentId);
    }
}
