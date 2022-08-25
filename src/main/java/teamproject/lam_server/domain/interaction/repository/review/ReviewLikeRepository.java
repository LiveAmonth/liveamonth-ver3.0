package teamproject.lam_server.domain.interaction.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.interaction.entity.review.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

}
