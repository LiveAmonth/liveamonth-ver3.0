package teamproject.lam_server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.schedule.entity.Schedule;

import java.util.List;
import static teamproject.lam_server.constants.AttrConstants.*;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

}
