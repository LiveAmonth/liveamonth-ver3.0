package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.repository.ReviewRepository;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.paging.PageableDTO;

@Service
@Transactional(readOnly = true)
public class ReviewCommentService extends CommentService {
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRepository reviewRepository;

    public ReviewCommentService(MemberRepository memberRepository,
                                JwtTokenProvider jwtTokenProvider,
                                ReviewCommentRepository reviewCommentRepository,
                                ReviewRepository reviewRepository) {
        super(memberRepository, jwtTokenProvider);
        this.reviewCommentRepository = reviewCommentRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.REVIEW;
    }

    @Override
    public Long writeComment(String accessToken, Long contentId, Long commentId, WriteCommentRequest request) {
        Member member = getMemberFromAuthentication(accessToken);

        Review review = reviewRepository.findById(contentId).orElseThrow(ReviewNotFound::new);

        ReviewComment comment = request.toReviewEntity(member)
                .review(review)
                .parent(reviewCommentRepository.findById(commentId).orElse(null))
                .build();

        return reviewCommentRepository.save(comment).getId();
    }

    @Override
    public Page<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        return null;
    }
}
