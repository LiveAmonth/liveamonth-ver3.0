package teamproject.lam_server.domain.interaction.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleLike;

public interface ScheduleLikeRepository extends JpaRepository<ScheduleLike, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_like (from_member_id, to_schedule_id) " +
            "values(:#{#request.from}, :#{#request.to})"
            , nativeQuery = true)
    void like(@Param("request") InteractionRequest request);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from schedule_like " +
            "where from_member_id = :#{#request.from} " +
            "and to_schedule_id = :#{#request.to};"
            , nativeQuery = true)
    void cancelLike(@Param("request") InteractionRequest request);

    @Query(value = "select exists" +
            "(select count(1) " +
            "from schedule_like sl " +
            "inner join member m " +
            "on m.member_id = sl.from_member_id " +
            "where sl.to_schedule_id = :scheduleId " +
            "and m.login_id = :loginId)"
            , nativeQuery = true)
    boolean isMemberLike(@Param("loginId") String loginId, @Param("scheduleId") Long scheduleId);
}
