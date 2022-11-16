package teamproject.lam_server.domain.schedule.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

public interface ScheduleContentRepository extends JpaRepository<ScheduleContent, Long> {
}
