package teamproject.lam_simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamproject.lam_simple.domain.Schedule;
import teamproject.lam_simple.domain.ScheduleContent;

import java.util.List;

import static teamproject.lam_simple.constants.AttrConstants.*;

@Repository
public interface ScheduleContentRepository extends JpaRepository<ScheduleContent,Long> {

    @Query(nativeQuery = true,value = "select *" +
            " from schedule_contents" +
            " where schedule_id = :id" +
            " and schedule_content_date between :startD and :endD" +
            " order by schedule_content_date, schedule_content_id")
    List<ScheduleContent> scheduleContentList(@Param(ID) long id,@Param(START_DATE) String start, @Param(END_DATE) String end);
}
