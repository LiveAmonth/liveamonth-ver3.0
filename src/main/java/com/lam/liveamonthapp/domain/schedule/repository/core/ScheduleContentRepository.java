package com.lam.liveamonthapp.domain.schedule.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lam.liveamonthapp.domain.schedule.entity.ScheduleContent;

public interface ScheduleContentRepository extends JpaRepository<ScheduleContent, Long> {
}
