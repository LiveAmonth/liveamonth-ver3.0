package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.BestCommentResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.repository.ReviewCommentQueryRepository;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewCommentService extends CommentService {
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewCommentQueryRepository reviewCommentQueryRepository;

    public ReviewCommentService(SecurityContextFinder finder, ReviewCommentRepository reviewCommentRepository, ReviewCommentQueryRepository reviewCommentQueryRepository) {
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
        Member writer = finder.getLoggedInMember();
        reviewCommentRepository.write(writer.getLoginId(), writer.getId(), contentId, request);
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
