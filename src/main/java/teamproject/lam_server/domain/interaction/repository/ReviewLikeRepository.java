package teamproject.lam_server.domain.interaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.interaction.entity.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long>, ReviewLikeRepositoryCustom {

}
