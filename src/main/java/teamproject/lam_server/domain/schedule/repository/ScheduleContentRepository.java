package teamproject.lam_server.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

public interface ScheduleContentRepository extends JpaRepository<ScheduleContent,Long>, ScheduleContentRepositoryCustom {

}
