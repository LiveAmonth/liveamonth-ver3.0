package teamproject.lam_server.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

@Service
@Transactional(readOnly = true)
public class ReviewCommentService extends CommentService {
    private final ReviewCommentRepository reviewCommentRepository;

    public ReviewCommentService(SecurityContextFinder finder, ReviewCommentRepository reviewCommentRepository) {
        super(finder);
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.REVIEW;
    }

    @Override
    @Transactional
    public void writeComment(Long contentId, CommentCreate request) {
        reviewCommentRepository.write(finder.getLoggedInMember(), contentId, request);
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
        return null;
    }
}
