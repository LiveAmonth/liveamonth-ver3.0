package teamproject.lam_simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_simple.domain.User;

import java.util.Optional;
import static teamproject.lam_simple.constants.AttrConstants.*;

@Repository
public interface LoginRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByLoginId(String loginId);
    Optional<User> findUserByNameAndEmail(String name, String email);
    Optional<User> findUserByLoginIdAndEmail(String loginId, String email);
    @Transactional
    @Modifying
    @Query(value = "update User u set u.password = :temporaryPw where u.id=:#{#user.id}")
    Integer editPassword(@Param(USER) User user, @Param(TEMPORARY_PW) String temporaryPw);

}
