package teamproject.lam_server.domain.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepositoryCustom {

    Page<Schedule> search(ScheduleSearchCond cond, Pageable pageable);

    Optional<Schedule> getScheduleAndContents(Long id);

    List<ScheduleContent> getScheduleContents(Long scheduleId);

    List<Schedule> getScheduleByMember(String loginId);

}
