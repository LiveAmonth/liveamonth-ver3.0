package com.lam.liveamonthapp.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;
import com.lam.liveamonthapp.domain.comment.entity.ReviewComment;
import com.lam.liveamonthapp.domain.comment.entity.ScheduleComment;
import com.lam.liveamonthapp.domain.comment.repository.ReviewCommentRepository;
import com.lam.liveamonthapp.domain.comment.repository.ScheduleCommentRepository;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.domain.review.entity.Review;
import com.lam.liveamonthapp.domain.review.repository.core.ReviewRepository;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.domain.schedule.repository.core.ScheduleRepository;
import com.lam.liveamonthapp.exception.notfound.CommentNotFound;
import com.lam.liveamonthapp.util.JsonUtil;

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
