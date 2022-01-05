package teamproject.lam_server.app.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.app.review.domain.Review;
import teamproject.lam_server.app.review.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/top-five")
    public List<Review> getTop5ReviewList() {
        return reviewService.findTOP5Reviews();
    }
}
