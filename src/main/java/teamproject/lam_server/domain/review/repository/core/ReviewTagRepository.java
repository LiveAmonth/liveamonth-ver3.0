package teamproject.lam_server.domain.review.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.review.entity.ReviewTag;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {
}
