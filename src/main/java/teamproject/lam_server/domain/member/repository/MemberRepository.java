package teamproject.lam_server.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNameAndEmail(String name, String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByLoginIdAndEmail(String loginId, String email);

    Optional<Member> findById(Long Id);

    Boolean existsByEmail(String email);

    Boolean existsByLoginId(String loginId);

    Boolean existsByNickname(String nickname);

}
