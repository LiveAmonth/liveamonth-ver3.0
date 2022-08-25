package teamproject.lam_server.domain.interaction.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleCommentDislike;

public interface ScheduleCommentDislikeRepository extends JpaRepository<ScheduleCommentDislike, Long> {


    @Modifying
    @Transactional
    @Query(value = "insert into schedule_comment_dislike (from_member_id, to_schedule_comment_id) values(:#{#request.from}, :#{#request.to})", nativeQuery = true)
    void dislike(@Param("request") InteractionRequest request);

    @Modifying
    @Transactional
    @Query(value = "delete from schedule_comment_dislike where from_member_id = :#{#request.from} and to_schedule_comment_id = :#{#request.to};", nativeQuery = true)
    void cancelDislike(@Param("request") InteractionRequest request);

    @Query(value = "select exists(select 1 from schedule_comment_like where from_member_id = :#{#request.from} and to_schedule_comment_id = :#{#request.to})" +
            " or " +
            "exists(select 1 from schedule_comment_dislike where from_member_id = :#{#request.from} and to_schedule_comment_id = :#{#request.to})", nativeQuery = true)
    boolean existsDislike(@Param("request") InteractionRequest request);
}
