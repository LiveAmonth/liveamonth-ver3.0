package teamproject.lam_simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamproject.lam_simple.domain.Schedule;
import teamproject.lam_simple.domain.User;

import java.util.List;
import static teamproject.lam_simple.constants.AttrConstants.*;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findTop5ByOrderByViewCountDesc();


    @Query(
            value = "select year(schedule_content_date)" +
                    " from schedule_contents" +
                    " where schedule_id = :id" +
                    " group by year(schedule_content_date)" +
                    " order by count(schedule_content_date) desc" +
                    " limit 1",nativeQuery = true
    )
    String getTopContentToYear(@Param(ID) long id);

    @Query(
            value = "select month(schedule_content_date)" +
                    " from schedule_contents" +
                    " where schedule_id = :id" +
                    " group by month(schedule_content_date)" +
                    " order by count(schedule_content_date) desc" +
                    " limit 1;",nativeQuery = true
    )
    String getTopContentToMoth(@Param(ID) long id);


}
