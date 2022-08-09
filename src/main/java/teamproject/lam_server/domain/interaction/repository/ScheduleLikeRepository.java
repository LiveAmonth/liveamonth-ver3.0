package teamproject.lam_server.domain.interaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.interaction.entity.ScheduleLike;

public interface ScheduleLikeRepository extends JpaRepository<ScheduleLike, Long>, ScheduleLikeRepositoryCustom {

}
