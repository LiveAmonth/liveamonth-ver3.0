package teamproject.lam_simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_simple.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {


    List<Review> findTop5ByOrderByViewCountDesc();

}
