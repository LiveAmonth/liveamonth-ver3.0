package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.paging.PageableDTO;

@Service
@Transactional(readOnly = true)
public class ReviewCommentService extends CommentService {
    private final ReviewCommentRepository reviewCommentRepository;

    public ReviewCommentService(MemberRepository memberRepository,
                                ReviewCommentRepository reviewCommentRepository) {
        super(memberRepository);
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.REVIEW;
    }

    @Override
    @Transactional
    public void writeComment(String loginId, WriteCommentRequest request) {
        Member member = super.findMemberByLoginId(loginId);
        reviewCommentRepository.write(member.getId(), request);
    }

    @Override
    public CommentResponse getBestComments(Long contentId) {
        return null;
    }

    @Override
    public Page<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        return null;
    }
}
