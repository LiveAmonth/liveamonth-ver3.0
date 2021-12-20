package teamproject.lam_simple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.lam_simple.domain.Review;
import teamproject.lam_simple.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> findTOP5Reviews(){
        return reviewRepository.findTop5ByOrderByViewCountDesc();
    }

}
