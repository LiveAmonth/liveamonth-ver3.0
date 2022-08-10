package teamproject.lam_server.domain.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.schedule.dto.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

import java.util.List;

public interface ScheduleRepositoryCustom {

    Page<Schedule> search(ScheduleSearchCond cond, Pageable pageable);


}
