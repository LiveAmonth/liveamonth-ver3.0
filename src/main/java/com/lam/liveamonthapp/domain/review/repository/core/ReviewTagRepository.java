package com.lam.liveamonthapp.domain.review.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lam.liveamonthapp.domain.review.entity.ReviewTag;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {
}
