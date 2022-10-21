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
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());

        Page<ReviewComment> reviewComments = reviewCommentRepository.getComments(contentId, pageable);

        List<ReviewComment> reviewCommentReplies = reviewComments.getNumberOfElements() != 0
                ? getReviewCommentReplies(contentId, reviewComments.getContent())
                : Collections.emptyList();

        Page<CommentResponse> page = reviewComments.map(comment -> mapToCommentResponse(
                CommentResponse.of(comment),
                comment.getId(),
                reviewCommentReplies));

        return CustomPage.<CommentResponse>builder()
                .page(page)
                .build();
    }
    @Override
    public List<BestCommentResponse> getBestComments(Long contentId) {
        return reviewCommentRepository.getBestComments(contentId).stream()
                .map(BestCommentResponse::of)
                .collect(Collectors.toList());
    }

    private List<ReviewComment> getReviewCommentReplies(Long reviewId, List<ReviewComment> comments) {
        Long from = comments.get(comments.size() - 1).getId();
        Long to = comments.get(0).getId();
        return reviewCommentRepository.getCommentReplies(reviewId, from, to);
    }
}
