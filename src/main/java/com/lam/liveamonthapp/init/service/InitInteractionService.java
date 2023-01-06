package com.lam.liveamonthapp.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.repository.member.FollowRepository;
import com.lam.liveamonthapp.domain.interaction.repository.review.ReviewLikeRepository;
import com.lam.liveamonthapp.domain.interaction.repository.schedule.ScheduleLikeRepository;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.domain.review.entity.Review;
import com.lam.liveamonthapp.domain.review.repository.core.ReviewRepository;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.domain.schedule.repository.core.ScheduleRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitInteractionService {
    private final FollowRepository followRepository;
    private final ScheduleLikeRepository scheduleLikeRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void initInteractionData() {
        List<Member> members = memberRepository.findAll();
        for (int i = members.size() - 1; i > 1; i--) {
            InteractionRequest request = new InteractionRequest();
            if (i % 2 == 0 && i > 3) {
                request.setFrom(members.get(i).getId());
                request.setTo(members.get(i - 2).getId());
                followRepository.follow(request);
            }

            request.setFrom(members.get(i).getId());
            request.setTo(members.get(i - 1).getId());
            followRepository.follow(request);

            request.setFrom(members.get(i).getId());
            request.setTo(members.get(0).getId());
            followRepository.follow(request);
        }
        for (int i = 0; i < members.size() - 2; i++) {
            InteractionRequest request = new InteractionRequest();
            if (i % 2 == 0 && i > 1 && i < members.size() - 3) {
                request.setFrom(members.get(i).getId());
                request.setTo(members.get(i + 3).getId());
                followRepository.follow(request);
            }

            request.setFrom(members.get(i).getId());
            request.setTo(members.get(i + 1).getId());
            followRepository.follow(request);

            request.setFrom(members.get(0).getId());
            request.setTo(members.get(i).getId());
            followRepository.follow(request);
        }
    }

    @Transactional
    public void initScheduleLikeData() {
        List<Member> members = memberRepository.findAll();
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Member member : members) {
            InteractionRequest request = new InteractionRequest();
            request.setFrom(member.getId());
            request.setTo(schedules.get(0).getId());
            scheduleLikeRepository.like(request);

            request.setTo(schedules.get((int) ((schedules.size() - 1) * Math.random() + 1)).getId());
            scheduleLikeRepository.like(request);
        }
    }

    @Transactional
    public void initReviewLikeData() {
        List<Member> members = memberRepository.findAll();
        List<Review> reviews = reviewRepository.findAll();
        for (Member member : members) {
            InteractionRequest request = new InteractionRequest();
            request.setFrom(member.getId());

            request.setTo(reviews.get(0).getId());
            reviewLikeRepository.like(request);

            request.setTo(reviews.get((int) ((reviews.size() - 1) * Math.random() + 1)).getId());
            reviewLikeRepository.like(request);
        }

    }
}
