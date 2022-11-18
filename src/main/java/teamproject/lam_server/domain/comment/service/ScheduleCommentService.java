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
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentQueryRepository;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleCommentService extends CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;
    private final ScheduleCommentQueryRepository scheduleCommentQueryRepository;

    public ScheduleCommentService(SecurityContextFinder finder,
                                  ScheduleCommentRepository scheduleCommentRepository, ScheduleCommentQueryRepository scheduleCommentQueryRepository) {
        super(finder);
        this.scheduleCommentRepository = scheduleCommentRepository;
        this.scheduleCommentQueryRepository = scheduleCommentQueryRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.SCHEDULE;
    }

    @Override
    @Transactional
    public void writeComment(Long contentId, CommentCreate request) {
        Member writer = finder.getLoggedInMember();
        scheduleCommentRepository.write(writer.getLoginId(), writer.getId(), contentId, request);
    }

    @Override
    @Transactional
    public void editComment(Long commentId, CommentEdit request) {
        super.edit(
                scheduleCommentRepository
                        .findById(commentId)
                        .orElseThrow(CommentNotFound::new),
                request
        );
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        ScheduleComment comment = scheduleCommentRepository
                .findById(commentId)
                .orElseThrow(CommentNotFound::new);
        finder.checkLegalWriterOfPost(comment);
        scheduleCommentRepository.delete(comment);
    }

    @Override
    public CustomPage<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        Page<CommentResponse> reviewComments = scheduleCommentQueryRepository.getComments(contentId, pageable);

        return CustomPage.<CommentResponse>builder()
                .page(reviewComments)
                .build();
    }

    @Override
    public List<BestCommentResponse> getBestComments(Long contentId) {
        return scheduleCommentQueryRepository.getBestComments(contentId);
    }
}
