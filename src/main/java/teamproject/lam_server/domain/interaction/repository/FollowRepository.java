package teamproject.lam_server.domain.interaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.interaction.entity.Follower;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follower, Long>, FollowRepositoryCustom {

    Optional<Follower> findByFromAndTo(Member from, Member to);

}
