package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.repository.core.ReviewRepository;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
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
            Schedule schedule = scheduleRepository.findAll().get((int) ((scheduleSize - 1) * Math.random()));

            ScheduleComment comment = request.getParentId() == null
                    ? request.toScheduleEntity(member, schedule)
                    : request.toScheduleEntity(member, schedule, scheduleCommentRepository.findById(request.getParentId()).orElseThrow(CommentNotFound::new));

            scheduleCommentRepository.save(comment);
        }
    }

    @Transactional
    public void initReviewCommentData() {
        List<CommentCreate> editors = JsonUtil.jsonArrayToList(COMMENT, CommentCreate.class);
        long reviewSize = reviewRepository.count();
        long memberSize = memberRepository.count();
        for (CommentCreate request : editors) {
            Member member = memberRepository.findAll().get((int) ((memberSize - 1) * Math.random()));
            Review review = reviewRepository.findAll().get((int) ((reviewSize - 1) * Math.random()));

            ReviewComment comment = request.getParentId() == null
                    ? request.toReviewEntity(member, review)
                    : request.toReviewEntity(member, review, reviewCommentRepository.findById(request.getParentId()).orElseThrow(CommentNotFound::new));

            reviewCommentRepository.save(comment);
        }
    }
}
