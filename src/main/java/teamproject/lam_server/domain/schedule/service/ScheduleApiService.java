package teamproject.lam_server.domain.schedule.service;

import teamproject.lam_server.domain.schedule.dto.CreateSchedule;

public interface ScheduleApiService {

    void addSchedule(Long memberId, CreateSchedule request);
}
