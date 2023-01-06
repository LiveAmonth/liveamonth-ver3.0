package com.lam.liveamonthapp.domain.review.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Transactional
    @Modifying
    @Query("update Review r set r.numberOfHits = r.numberOfHits + 1 where r.id =:id")
    void viewCountUp(@Param("id") Long id);
}
