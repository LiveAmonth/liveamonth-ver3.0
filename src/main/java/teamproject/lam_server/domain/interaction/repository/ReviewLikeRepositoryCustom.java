package teamproject.lam_server.domain.interaction.repository;

import teamproject.lam_server.domain.interaction.entity.ReviewLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepositoryCustom {
    Optional<ReviewLike> findById(Long from, Long to);

    Long getReviewLikesCount(Long to);

    List<Member> getReviewLikes(Long to);

    Long getLikedReviewsCount(Long from);

    List<Review> getLikedReviews(Long from);
}
