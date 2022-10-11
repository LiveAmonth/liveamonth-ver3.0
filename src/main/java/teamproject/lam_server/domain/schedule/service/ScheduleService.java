package teamproject.lam_server.domain.schedule.service;

import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleEdit;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

public interface ScheduleService {

    void addSchedule(ScheduleCreate request);

    void editSchedule(Long scheduleId, ScheduleEdit request);

    void deleteSchedule(Long scheduleId);

    CustomPage<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO);

    List<ScheduleCardResponse> getScheduleByMember(String loginId, Integer size, Long lastId);

    List<ScheduleCardResponse> getFollowedSchedules(String loginId, Integer size, Long lastId);
}
