package teamproject.lam_server.domain.review.service;

import teamproject.lam_server.domain.review.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findTOP5Reviews();
}
