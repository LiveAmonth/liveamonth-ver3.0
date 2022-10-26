package teamproject.lam_server.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    Page<Review> search(ReviewSearchCond cond, List<Long> reviewTagIds, Pageable pageable);

    List<Review> getReviewByMember(String loginId, Integer size, Long lastId);

    Optional<Review> getReview(Long id);

    List<String> getRecommendationTags();
}
