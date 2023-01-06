package com.lam.liveamonthapp.domain.review.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lam.liveamonthapp.domain.review.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
