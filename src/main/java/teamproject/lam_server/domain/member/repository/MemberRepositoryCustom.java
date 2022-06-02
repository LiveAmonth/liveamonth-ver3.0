package teamproject.lam_server.domain.member.repository;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Long cleanDeleteById(Long id);

    Optional<String> findLoginIdByNameAndEmail(String name, String email);

}
