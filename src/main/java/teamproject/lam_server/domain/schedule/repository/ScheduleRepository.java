package teamproject.lam_server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.dto.editor.ScheduleEditor;
import teamproject.lam_server.domain.schedule.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule (created_date, last_modified_date, member_id, title, city_name, start_date, end_date, public_flag, view_count) " +
            "values(now(), now(), :memberId, :#{#request.title}, :#{#request.city.code}, :#{#request.period.startDate}, :#{#request.period.endDate}, :#{#request.publicFlag}, 0)"
            , nativeQuery = true)
    void addSchedule(@Param("memberId") Long memberId, @Param("request") ScheduleEditor request);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from schedule s " +
            "where s.schedule_id = :id"
            , nativeQuery = true)
    void deleteSchedule(@Param("id") Long id);
}
