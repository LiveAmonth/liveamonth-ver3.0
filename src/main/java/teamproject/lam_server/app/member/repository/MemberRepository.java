package teamproject.lam_server.app.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.member.domain.Member;

import java.util.Optional;

import static teamproject.lam_server.constants.AttrConstants.*;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNameAndEmail(String name, String email);

    Optional<Member> findByLoginIdAndEmail(String loginId, String email);

    Optional<Member> findById(Long Id);

    Boolean existsByEmail(String email);

    Boolean existsByLoginId(String loginId);

    Boolean existsByNickname(String nickname);

    @Transactional
    @Modifying
    @Query(value = "update Member u set u.password = :temporaryPw where u.id=:#{#member.id}")
    Integer editPassword(@Param(USER) Member member, @Param(TEMPORARY_PW) String temporaryPw);

    @Transactional
    @Modifying
    @Query(value = "update Member u set u.image = :image where u.id=:id")
    Integer editImage(@Param(ID) Long id, @Param(IMAGE) String image);

}
