package teamproject.lam_server.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.entity.Review;

public interface ReviewRepositoryCustom {
    Page<Review> search(ReviewSearchCond cond, Pageable pageable);
}
