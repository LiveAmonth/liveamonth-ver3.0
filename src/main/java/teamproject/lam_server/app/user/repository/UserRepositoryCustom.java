package teamproject.lam_server.app.user.repository;

import java.util.Optional;

public interface UserRepositoryCustom {

    Long cleanDeleteById(Long id);

    Optional<String> findLoginId(String name, String email);

}
