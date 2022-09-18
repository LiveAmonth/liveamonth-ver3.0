package teamproject.lam_server.domain.schedule.service;

import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.editor.ScheduleEditor;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleSimpleCardResponse;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

public interface ScheduleService {

    void addSchedule(Long memberId, ScheduleEditor request);

    void editSchedule(Long scheduleId, ScheduleEditor request);

    void deleteSchedule(Long scheduleId);

    CustomPage<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO);

    List<ScheduleSimpleCardResponse> getScheduleByMember(String loginId, Integer size, Long lastId);
}
