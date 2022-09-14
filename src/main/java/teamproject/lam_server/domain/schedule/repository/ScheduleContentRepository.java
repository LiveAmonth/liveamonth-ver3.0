package teamproject.lam_server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.dto.editor.ScheduleContentEditor;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

public interface ScheduleContentRepository extends JpaRepository<ScheduleContent, Long> {

    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_content (created_date, last_modified_date, schedule_id, title, content, start_date_time, end_date_time, cost) " +
            "values(now(), now(), :scheduleId, :#{#request.title}, :#{#request.content}, :#{#request.timePeriod.startDateTime}, :#{#request.timePeriod.endDateTime}, :#{#request.cost})"
            , nativeQuery = true)
    void addContent(@Param("scheduleId") Long scheduleId, @Param("request") ScheduleContentEditor request);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from schedule_content sc " +
            "where sc.schedule_content_id = :contentId"
            , nativeQuery = true)
    void deleteContent(@Param("contentId") Long contentId);

}
