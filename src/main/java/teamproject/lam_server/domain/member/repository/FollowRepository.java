package teamproject.lam_server.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Followers;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Followers, Long>,FollowRepositoryCustom {

    Optional<Followers> findByFromAndTo(Member from, Member to);

    @Transactional
    void deleteByFromAndTo(Member from, Member to);
}
