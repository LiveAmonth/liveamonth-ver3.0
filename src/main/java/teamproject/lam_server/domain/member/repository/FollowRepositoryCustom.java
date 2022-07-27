package teamproject.lam_server.domain.member.repository;

import teamproject.lam_server.domain.member.dto.request.FollowRequest;
import teamproject.lam_server.domain.member.entity.Followers;

import java.util.Optional;

public interface FollowRepositoryCustom {

//    void unFollow(FollowRequest request);

    Optional<Followers> findByLoginId(FollowRequest request);
}
