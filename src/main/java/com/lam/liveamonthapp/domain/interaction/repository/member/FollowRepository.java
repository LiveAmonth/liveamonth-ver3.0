package com.lam.liveamonthapp.domain.interaction.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.entity.member.Follower;

public interface FollowRepository extends JpaRepository<Follower, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into follower (created_date, last_modified_date, from_member_id, to_member_id) " +
            "values(now(), now(), :#{#request.from}, :#{#request.to})"
            , nativeQuery = true)
    void follow(@Param("request") InteractionRequest request);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from follower " +
            "where from_member_id = :#{#request.from} " +
            "and to_member_id = :#{#request.to}"
            , nativeQuery = true)
    void unFollow(@Param("request") InteractionRequest request);
}
