package teamproject.lam_server.domain.interaction.service;

import teamproject.lam_server.domain.member.dto.request.InteractionRequest;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;

import java.util.List;

public interface InteractionService {

    void follow(InteractionRequest request);

    void unFollow(InteractionRequest request);

    void scheduleLike(InteractionRequest request);

    void scheduleUnLike(InteractionRequest request);

    void reviewLike(InteractionRequest request);

    void reviewUnLike(InteractionRequest request);

    Long getFollowersCount(Long to);

    Long getFollowingsCount(Long to);

    List<MemberProfileResponse> getFollowers(Long to);

    List<MemberProfileResponse> getFollowings(Long to);

    Long getScheduleLikesCount(Long to);

    List<MemberProfileResponse> getScheduleLikes(Long to);

    Long getLikedSchedulesCount(Long from);

    Long getReviewLikesCount(Long to);

    List<MemberProfileResponse> getReviewLikes(Long to);

    Long getLikedReviewsCount(Long from);
}
