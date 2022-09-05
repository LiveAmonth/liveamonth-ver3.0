package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.paging.PageableDTO;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleCommentService extends CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;

    public ScheduleCommentService(MemberRepository memberRepository,
                                  ScheduleCommentRepository scheduleCommentRepository) {
        super(memberRepository);
        this.scheduleCommentRepository = scheduleCommentRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.SCHEDULE;
    }

    @Override
    @Transactional
    public void writeComment(String loginId, WriteCommentRequest request) {
        Member member = super.findMemberByLoginId(loginId);
        scheduleCommentRepository.write(member.getId(), request);
    }

    @Override
    public Page<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        // 페이지 정보
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());

        // 스케줄 댓글
        Page<ScheduleComment> scheduleComments = scheduleCommentRepository.getComments(contentId, pageable);

        // 스케줄 대댓글
        List<ScheduleComment> scheduleCommentReplies = scheduleComments.getNumberOfElements() != 0
                ? getScheduleCommentReplies(contentId, scheduleComments.getContent())
                : Collections.emptyList();

        return scheduleComments.map(comment -> mapToCommentResponse(
                CommentResponse.of(comment),
                comment.getId(),
                scheduleCommentReplies));
    }

    @Override
    public CommentResponse getBestComments(Long contentId) {
        ScheduleComment scheduleComment = scheduleCommentRepository.getBestComment(contentId).orElseThrow(CommentNotFound::new);
        List<ScheduleComment> commentReplies = getScheduleCommentReplies(contentId, List.of(scheduleComment));
        return mapToCommentResponse(CommentResponse.of(scheduleComment), scheduleComment.getId(), commentReplies);
    }

    private List<ScheduleComment> getScheduleCommentReplies(Long scheduleId, List<ScheduleComment> comments) {
        Long from = comments.get(comments.size() - 1).getId();
        Long to = comments.get(0).getId();
        return scheduleCommentRepository.getCommentReplies(scheduleId, from, to);
    }
}
