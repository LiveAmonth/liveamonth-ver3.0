package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.paging.PageableDTO;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleCommentService extends CommentService {
    private final ScheduleCommentRepository scheduleCommentRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleCommentService(MemberRepository memberRepository,
                                  JwtTokenProvider jwtTokenProvider,
                                  ScheduleCommentRepository scheduleCommentRepository,
                                  ScheduleRepository scheduleRepository) {
        super(memberRepository, jwtTokenProvider);
        this.scheduleCommentRepository = scheduleCommentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public CommentType getType() {
        return CommentType.SCHEDULE;
    }

    @Override
    @Transactional
    public void writeComment(String accessToken, Long contentId, Long commentId, WriteCommentRequest request) {
        // access token 으로 작성자 검색
        Member member = getMemberFromAuthentication(accessToken);

        // 댓글을 작성한 게시물 확인
//        Schedule schedule = scheduleRepository.findById(contentId).orElseThrow(ScheduleNotFound::new);
//
//        ScheduleComment comment = request.toScheduleEntity(member)
//                .schedule(schedule)
//                .parent(scheduleCommentRepository.findById(commentId).orElse(null))
//                .build();
//        return scheduleCommentRepository.save(comment).getId();
        scheduleCommentRepository.write(request, member.getId(), contentId, commentId);
    }

    @Override
    public Page<CommentResponse> getComments(Long contentId, PageableDTO pageableDTO) {
        Pageable pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());
        Page<ScheduleComment> scheduleComments = scheduleCommentRepository.getScheduleComments(contentId, pageable);
        List<ScheduleComment> scheduleCommentReplies = scheduleComments.getNumberOfElements() != 0
                ? getScheduleCommentReplies(contentId, scheduleComments)
                : Collections.emptyList();
        return scheduleComments.map(comment -> mapToCommentResponse(
                CommentResponse.of(comment),
                comment.getId(),
                scheduleCommentReplies));
    }

    private List<ScheduleComment> getScheduleCommentReplies(Long scheduleId, Page<ScheduleComment> comments) {
        Long from = comments.getContent().get(comments.getNumberOfElements() - 1).getId();
        Long to = comments.getContent().get(0).getId();
        return scheduleCommentRepository.getScheduleCommentReplies(scheduleId, from, to);
    }
}
