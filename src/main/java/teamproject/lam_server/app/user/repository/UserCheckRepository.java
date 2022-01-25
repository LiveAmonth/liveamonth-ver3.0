package teamproject.lam_server.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.app.user.domain.User;

public interface UserCheckRepository extends JpaRepository<User, Long> {

    Boolean existsByLoginId(String loginId);
    Boolean existsByEmail(String Email);
    Boolean existsByNickname(String nickname);

    Boolean existsByLoginIdAndPassword(String loginId, String password);
}
