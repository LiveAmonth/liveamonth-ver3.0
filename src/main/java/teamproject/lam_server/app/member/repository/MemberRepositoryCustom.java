package teamproject.lam_server.app.member.repository;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Long cleanDeleteById(Long id);

    Optional<String> findLoginIdByNameAndEmail(String name, String email);

}
