package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleCommentService extends CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;

    public ScheduleCommentService(SecurityContextFinder finder,
                                  ScheduleCommentRepository scheduleCommentRepository) {
        super(finder);
        this.scheduleCommentRepository = scheduleCommentRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.SCHEDULE;
    }

    @Override
    @Transactional
    public void writeComment(Long contentId, CommentCreate request) {
        scheduleCommentRepository.write(finder.getLoggedInMember(), contentId, request);
    }

    @Override
    @Transactional
    public void editComment(Long commentId, CommentEdit request) {
        super.edit(
                scheduleCommentRepository.findById(commentId).orElseThrow(CommentNotFound::new),
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
        // 페이지 정보
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());

        // 스케줄 댓글
        Page<ScheduleComment> scheduleComments = scheduleCommentRepository.getComments(contentId, pageable);

        // 스케줄 대댓글
        List<ScheduleComment> scheduleCommentReplies = scheduleComments.getNumberOfElements() != 0
                ? getScheduleCommentReplies(contentId, scheduleComments.getContent())
                : Collections.emptyList();

        Page<CommentResponse> page = scheduleComments.map(comment -> mapToCommentResponse(
                CommentResponse.of(comment),
                comment.getId(),
                scheduleCommentReplies));

        return CustomPage.<CommentResponse>builder()
                .page(page)
                .build();
    }

    private List<ScheduleComment> getScheduleCommentReplies(Long scheduleId, List<ScheduleComment> comments) {
        Long from = comments.get(comments.size() - 1).getId();
        Long to = comments.get(0).getId();
        return scheduleCommentRepository.getCommentReplies(scheduleId, from, to);
    }
}
