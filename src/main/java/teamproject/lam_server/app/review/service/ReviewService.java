package teamproject.lam_server.app.review.service;

import teamproject.lam_server.app.review.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findTOP5Reviews();
}
