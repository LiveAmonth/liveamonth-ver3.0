package teamproject.lam_server.app.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.lam_server.app.review.domain.Review;
import teamproject.lam_server.app.review.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;

    public List<Review> findTOP5Reviews(){
        return reviewRepository.findTop5ByOrderByViewCountDesc();
    }

}
