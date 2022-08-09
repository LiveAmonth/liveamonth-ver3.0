package teamproject.lam_server.domain.interaction.repository;

import teamproject.lam_server.domain.interaction.entity.Follower;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface FollowRepositoryCustom {

    Optional<Follower> findByMemberId(Long from, Long id);

    Long getFollowersCount(Long to);

    Long getFollowingsCount(Long to);

    List<Member> getFollowers(Long to);

    List<Member> getFollowings(Long from);
}
