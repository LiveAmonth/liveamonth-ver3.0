package teamproject.lam_server.domain.interaction.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.entity.schedule.ScheduleCommentReact;

import java.util.List;
import java.util.Optional;

public interface ScheduleCommentReactRepository extends JpaRepository<ScheduleCommentReact, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_comment_react (from_member_id, to_schedule_comment_id, type) " +
            "values(:#{#request.from}, :#{#request.to}, :type);"
            , nativeQuery = true)
    void react(@Param("request") InteractionRequest request, @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from schedule_comment_react scr " +
            "where scr.from_member_id = :#{#request.from} " +
            "and scr.to_schedule_comment_id = :#{#request.to} " +
            "and scr.type = :type"
            , nativeQuery = true)
    void cancelReact(@Param("request") InteractionRequest request, @Param("type") ReactType type);

    @Query(value = "" +
            "select type from schedule_comment_react scr " +
            "where scr.from_member_id = :#{#request.from} " +
            "and scr.to_schedule_comment_id = :#{#request.to}"
            , nativeQuery = true)
    Optional<ReactType> existsReact(@Param("request") InteractionRequest request);

    @Query(value = "" +
            "select * from schedule_comment_react scr " +
            "where scr.from_member_id = :memberId " +
            "and scr.to_schedule_comment_id in :ids"
            , nativeQuery = true)
    List<ScheduleCommentReact> getReactedComments(@Param("memberId")Long memberId, @Param("ids") List<Long> ids);

}
