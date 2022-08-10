package teamproject.lam_server.domain.schedule.repository;

import teamproject.lam_server.domain.schedule.entity.ScheduleContent;

import java.util.List;

public interface ScheduleContentRepositoryCustom {

    List<ScheduleContent> getScheduleContents(Long scheduleId);
}
