package com.lam.liveamonthapp.domain.schedule.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> getByCreatedBy(String createdBy);

    @Transactional
    @Modifying
    @Query("update Schedule s set s.numberOfHits = s.numberOfHits + 1 where s.id =:id")
    void viewCountUp(@Param("id") Long id);
}
