package com.lam.liveamonthapp.domain.interaction.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.entity.schedule.ScheduleLike;

public interface ScheduleLikeRepository extends JpaRepository<ScheduleLike, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_like (created_date, last_modified_date, from_member_id, to_schedule_id) " +
            "values(now(), now(), :#{#request.from}, :#{#request.to})"
            , nativeQuery = true)
    void like(@Param("request") InteractionRequest request);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from schedule_like sl " +
            "where sl.from_member_id = :#{#request.from} " +
            "and sl.to_schedule_id = :#{#request.to}"
            , nativeQuery = true)
    void cancelLike(@Param("request") InteractionRequest request);
}
