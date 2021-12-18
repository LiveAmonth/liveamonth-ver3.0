package teamproject.lam_simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_simple.domain.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findById(Long Id);

    Boolean existsByEmail(String email);

    Boolean existsByLoginId(String loginId);

    Boolean existsByNickname(String nickname);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.image = :image where u.id=:id")
    Integer editImage(@Param("id") Long id, @Param("image") String image);
}
