package teamproject.lam_server.domain.interaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.entity.Follower;
import teamproject.lam_server.domain.interaction.entity.ReviewLike;
import teamproject.lam_server.domain.interaction.entity.ScheduleLike;
import teamproject.lam_server.domain.interaction.repository.FollowRepository;
import teamproject.lam_server.domain.interaction.repository.ReviewLikeRepository;
import teamproject.lam_server.domain.interaction.repository.ScheduleLikeRepository;
import teamproject.lam_server.domain.member.dto.request.InteractionRequest;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.repository.ReviewRepository;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.exception.badrequest.AlreadyFollowMember;
import teamproject.lam_server.exception.badrequest.AlreadyLikeReview;
import teamproject.lam_server.exception.badrequest.AlreadyLikeSchedule;
import teamproject.lam_server.exception.notfound.FollowNotFound;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InteractionServiceImpl implements InteractionService {
    private final FollowRepository followRepository;
    private final ScheduleLikeRepository scheduleLikeRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void follow(InteractionRequest request) {
        if (followRepository.findByMemberId(request.getFrom(), request.getTo()).isPresent()) {
            throw new AlreadyFollowMember();
        }

        Member from = memberRepository.findById(request.getFrom()).orElseThrow(MemberNotFound::new);
        Member to = memberRepository.findById(request.getTo()).orElseThrow(MemberNotFound::new);

        Follower follower = Follower.builder()
                .from(from)
                .to(to)
                .build();

        followRepository.save(follower);
    }

    @Override
    @Transactional
    public void unFollow(InteractionRequest request) {
        Follower follower = followRepository
                .findByMemberId(request.getFrom(), request.getTo())
                .orElseThrow(FollowNotFound::new);

        follower.unFollow();
        followRepository.delete(follower);
    }

    @Override
    @Transactional
    public void scheduleLike(InteractionRequest request) {
        if (scheduleLikeRepository.findById(request.getFrom(), request.getTo()).isPresent()) {
            throw new AlreadyLikeSchedule();
        }

        Member from = memberRepository.findById(request.getFrom()).orElseThrow(MemberNotFound::new);
        Schedule to = scheduleRepository.findById(request.getTo()).orElseThrow(ScheduleNotFound::new);

        ScheduleLike scheduleLike = ScheduleLike.builder()
                .from(from)
                .to(to)
                .build();

        scheduleLikeRepository.save(scheduleLike);
    }

    @Override
    public void scheduleUnLike(InteractionRequest request) {
        ScheduleLike scheduleLike = scheduleLikeRepository
                .findById(request.getFrom(), request.getTo())
                .orElseThrow(ScheduleNotFound::new);

        scheduleLike.unFollow();
        scheduleLikeRepository.delete(scheduleLike);
    }

    @Override
    public void reviewLike(InteractionRequest request) {
        if (reviewLikeRepository.findById(request.getFrom(), request.getTo()).isPresent()) {
            throw new AlreadyLikeReview();
        }

        Member from = memberRepository.findById(request.getFrom()).orElseThrow(MemberNotFound::new);
        Review to = reviewRepository.findById(request.getTo()).orElseThrow(ReviewNotFound::new);

        ReviewLike reviewLike = ReviewLike.builder()
                .from(from)
                .to(to)
                .build();

        reviewLikeRepository.save(reviewLike);
    }

    @Override
    public void reviewUnLike(InteractionRequest request) {
        ReviewLike reviewLike = reviewLikeRepository
                .findById(request.getFrom(), request.getTo())
                .orElseThrow(ReviewNotFound::new);

        reviewLike.unFollow();
        reviewLikeRepository.delete(reviewLike);
    }

    @Override
    public Long getFollowersCount(Long to) {
        return followRepository.getFollowersCount(to);
    }

    @Override
    public List<MemberProfileResponse> getFollowers(Long to) {
        return followRepository.getFollowers(to).stream()
                .map(MemberProfileResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberProfileResponse> getFollowings(Long from) {
        return followRepository.getFollowings(from).stream()
                .map(MemberProfileResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public Long getFollowingsCount(Long from) {
        return followRepository.getFollowingsCount(from);
    }

    @Override
    public Long getScheduleLikesCount(Long to) {
        return scheduleLikeRepository.getScheduleLikesCount(to);
    }

    @Override
    public List<MemberProfileResponse> getScheduleLikes(Long to) {
        return scheduleLikeRepository.getScheduleLikes(to).stream()
                .map(MemberProfileResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public Long getLikedSchedulesCount(Long from) {
        return scheduleLikeRepository.getLikedSchedulesCount(from);
    }

//    @Override
//    public List<MemberProfileResponse> getLikedSchedules(Long from) {
//        return scheduleLikeRepository.getLikedSchedules(from).stream()
//                .map(MemberProfileResponse::of)
//                .collect(Collectors.toList());
//    }

    @Override
    public Long getReviewLikesCount(Long to) {
        return reviewLikeRepository.getReviewLikesCount(to);
    }

    @Override
    public List<MemberProfileResponse> getReviewLikes(Long to) {
        return reviewLikeRepository.getReviewLikes(to).stream()
                .map(MemberProfileResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public Long getLikedReviewsCount(Long from) {
        return reviewLikeRepository.getLikedReviewsCount(from);
    }

//    @Override
//    public List<SimpleReviewResponse> getLikedReviews(Long from) {
//        return reviewLikeRepository.getLikedReviews(from).stream()
//                .map(SimpleReviewResponse::of)
//                .collect(Collectors.toList());

//    }

}
