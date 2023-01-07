package com.lam.liveamonthapp.performace.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.lam.liveamonthapp.domain.review.entity.ReviewTag;

import java.util.List;

public interface ReviewTagTestRepository extends JpaRepository<ReviewTag, Long> {

    @Query(value = "select t.name from review_tag rt left join tag t on t.tag_id = rt.tag_id where rt.review_id = :id", nativeQuery = true)
    List<String> findTagNamesById(@Param("id") Long id);
}
