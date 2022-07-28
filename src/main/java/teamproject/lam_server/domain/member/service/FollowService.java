package teamproject.lam_server.domain.member.service;

import teamproject.lam_server.domain.member.dto.request.FollowRequest;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;

import java.util.List;

public interface FollowService {

    void follow(FollowRequest request);

    void unFollow(FollowRequest request);

    Long getFollowersCount(Long to);

    Long getFollowingsCount(Long to);

    List<MemberProfileResponse> getFollowers(Long to);

    List<MemberProfileResponse> getFollowings(Long to);
}
