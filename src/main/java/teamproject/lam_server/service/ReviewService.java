package teamproject.lam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.lam_server.domain.Review;
import teamproject.lam_server.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> findTOP5Reviews(){
        return reviewRepository.findTop5ByOrderByViewCountDesc();
    }

}
