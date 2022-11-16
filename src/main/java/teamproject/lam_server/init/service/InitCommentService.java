package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.review.repository.core.ReviewRepository;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitCommentService {
    private static final String COMMENT = "comment";
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleCommentRepository scheduleCommentRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    @Transactional
    public void initScheduleCommentData() {
        List<CommentCreate> editors = JsonUtil.jsonArrayToList(COMMENT, CommentCreate.class);
        long scheduleSize = scheduleRepository.count();
        long memberSize = memberRepository.count();
        for (CommentCreate request : editors) {
            Member member = memberRepository.findAll().get((int) ((memberSize - 1) * Math.random()));

            Long scheduleId = request.getParentId() == null
                    ? scheduleRepository.findAll().get((int) ((scheduleSize - 1) * Math.random())).getId()
                    : scheduleCommentRepository.findById(request.getParentId()).get().getSchedule().getId();

            scheduleCommentRepository.write(member, scheduleId, request);
        }
    }

    @Transactional
    public void initReviewCommentData() {
        List<CommentCreate> editors = JsonUtil.jsonArrayToList(COMMENT, CommentCreate.class);
        long reviewSize = reviewRepository.count();
        long memberSize = memberRepository.count();
        for (CommentCreate request : editors) {
            Member member = memberRepository.findAll().get((int) ((memberSize - 1) * Math.random()));

            Long scheduleId = request.getParentId() == null
                    ? reviewRepository.findAll().get((int) ((reviewSize - 1) * Math.random())).getId()
                    : reviewCommentRepository.findById(request.getParentId()).get().getReview().getId();

            reviewCommentRepository.write(member, scheduleId, request);
        }
    }
}
