package com.lam.liveamonthapp.domain.interaction.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.entity.schedule.ScheduleCommentInteraction;

import java.util.List;
import java.util.Optional;

public interface ScheduleCommentInteractionRepository extends JpaRepository<ScheduleCommentInteraction, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_comment_interaction (created_date, last_modified_date, from_member_id, to_schedule_comment_id, state) " +
            "values(now(), now(), :#{#request.from}, :#{#request.to}, :#{#state.name})"
            , nativeQuery = true)
    void interact(@Param("request") InteractionRequest request, @Param("state") InteractionState state);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from schedule_comment_interaction scr " +
            "where scr.from_member_id = :#{#request.from} " +
            "and scr.to_schedule_comment_id = :#{#request.to}"
            , nativeQuery = true)
    void cancelInteraction(@Param("request") InteractionRequest request);

    @Query(value = "" +
            "select scr.state from schedule_comment_interaction scr " +
            "where scr.from_member_id = :#{#request.from} " +
            "and scr.to_schedule_comment_id = :#{#request.to}"
            , nativeQuery = true)
    Optional<InteractionState> existsInteraction(@Param("request") InteractionRequest request);

    @Query(value = "" +
            "select * from schedule_comment_interaction scr " +
            "inner join schedule_comment sc on scr.to_schedule_comment_id = sc.schedule_comment_id " +
            "where scr.from_member_id = :memberId " +
            "and scr.to_schedule_comment_id in :ids"
            , nativeQuery = true)
    List<ScheduleCommentInteraction> getInteractedComments(@Param("memberId")Long memberId, @Param("ids") List<Long> ids);

}
