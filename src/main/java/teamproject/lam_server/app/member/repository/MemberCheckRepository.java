package teamproject.lam_server.app.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.app.member.domain.Member;

public interface MemberCheckRepository extends JpaRepository<Member, Long> {

    Boolean existsByLoginId(String loginId);
    Boolean existsByEmail(String Email);
    Boolean existsByNickname(String nickname);

    Boolean existsByLoginIdAndPassword(String loginId, String password);
}
